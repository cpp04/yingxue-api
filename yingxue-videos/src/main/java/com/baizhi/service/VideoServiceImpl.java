package com.baizhi.service;

import com.alibaba.druid.util.StringUtils;
import com.baizhi.dao.VideoDao;
import com.baizhi.entity.Category;
import com.baizhi.entity.Favorite;
import com.baizhi.entity.User;
import com.baizhi.entity.Video;
import com.baizhi.feignclients.CategoryClients;
import com.baizhi.feignclients.UserClients;
import com.baizhi.utils.JSONUtils;
import com.baizhi.utils.RedisPrefix;
import com.baizhi.vo.VideoDetailVO;
import com.baizhi.vo.VideoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 视频(Video)表服务实现类
 *
 * @author chenyn
 * @since 2020-11-12 17:56:35
 */
@Service("videoService")
@Transactional
public class VideoServiceImpl implements VideoService {


    private static final Logger log = LoggerFactory.getLogger(VideoServiceImpl.class);
    @Resource
    private VideoDao videoDao; //视频dao

    //调用用户服务
    @Autowired
    private UserClients userClients;

    //调用类别服务
    @Autowired
    private CategoryClients categoryClients;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;


    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public VideoVO queryById(Integer id) {
        return getVideoVO(this.videoDao.queryById(id));
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<VideoVO> queryAllByLimit(int offset, int limit) {
        offset = (offset - 1) * limit;
        //1.分页查询所有视频对象
        List<Video> videos = this.videoDao.queryAllByLimit(offset, limit);
        return getList(videos);
    }

    /**
     * 新增数据
     *
     * @param video 实例对象
     * @return 实例对象
     */
    @Override
    public Video insert(Video video) {
        video.setCreatedAt(new Date());//设置创建日期
        video.setUpdatedAt(new Date());//设置更新日期
        this.videoDao.insert(video);//保存视频到数据库

        //注意:利用MQ异步处理,提升系统响应: 将视频信息写入到ES索引库  1.在es中建立6.x  索引index  类型type   映射概念mapping   2.将视频信息写入es

        rabbitTemplate.convertAndSend("videos", "", JSONUtils.writeValueAsString(getVideoVO(video)));
        return video;
    }

    /**
     * 修改数据
     *
     * @param video 实例对象
     * @return 实例对象
     */
    @Override
    public Video update(Video video) {
        this.videoDao.update(video);
        return this.videoDao.queryById(video.getId());
    }

    @Override
    public Long findTotalCounts() {
        return this.videoDao.findTotalCounts();
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.videoDao.deleteById(id) > 0;
    }


    @Override
    public List<VideoVO> findAllByCategoryId(Integer page, Integer rows, Integer categoryId) {
        int start = (page - 1) * rows;
        List<Video> videos = videoDao.findAllByCategoryId(start, rows, categoryId);
        return getList(videos);
    }

    @Override
    public VideoDetailVO queryDetailById(String videoId, String token) {
        //1.根据id查询视频信息
        Video video = videoDao.queryById(Integer.valueOf(videoId));

        //2.创建videoDetailVo
        VideoDetailVO videoDetailVO = new VideoDetailVO();

        //3.将video对象中基本信息复制到VideoDetailVo {id title cover link created_at update_at}
        BeanUtils.copyProperties(video, videoDetailVO);

        //4.设置当前视频类别名称  VideoDetailVo {id title cover link created_at update_at category}
        Category category = categoryClients.category(video.getCategoryId().toString());//调用类别服务
        log.info("根据类别id查询到的类别信息为: {}", JSONUtils.writeValueAsString(category));
        videoDetailVO.setCategory(category.getName());


        //5.设置用户信息 VideoDetailVo {id title cover link created_at update_at category uploader}
        User user = userClients.user(video.getUid().toString());//调用用户服务
        log.info("根据id查询用户信息为: {}", JSONUtils.writeValueAsString(video));
        videoDetailVO.setUploader(user);

        //6.设置播放次数 {id title cover link created_at update_at category uploader plays_count}
        videoDetailVO.setPlaysCount(0);//初始化默认值为0
        String playedCounts = stringRedisTemplate.opsForValue().get(RedisPrefix.VIDEO_PLAYED_COUNT_PREFIX + videoId);
        if (!StringUtils.isEmpty(playedCounts)) {
            log.info("当前视频视频播放次数为: {}", playedCounts);
            videoDetailVO.setPlaysCount(Integer.valueOf(playedCounts));
        }


        //7.设置点赞数 {id title cover link created_at update_at category uploader plays_count likes_count}
        videoDetailVO.setLikesCount(0);//初始化默认值为0
        String likedCounts = stringRedisTemplate.opsForValue().get(RedisPrefix.VIDEO_LIKE_COUNT_PREFIX + video.getId());
        if (!StringUtils.isEmpty(likedCounts)) {
            log.info("当前视频点赞数量为: {}", likedCounts);
            videoDetailVO.setLikesCount(Integer.valueOf(likedCounts));
        }


        //8.设置当前用户是否喜欢  不喜欢  收藏 {id title cover link created_at update_at category uploader plays_count likes_count liked  disabled favorite}
        User o = (User) redisTemplate.opsForValue().get("session_" + token);
        if (!ObjectUtils.isEmpty(o)) {
            //设置是否喜欢
            Boolean liked = stringRedisTemplate.opsForSet().isMember(RedisPrefix.USER_LIKE_PREFIX + o.getId(), videoId);
            videoDetailVO.setLiked(liked);
            //设置是否不喜欢
            Boolean disliked = stringRedisTemplate.opsForSet().isMember(RedisPrefix.USER_DISLIKE_PREFIX + o.getId(), videoId);
            videoDetailVO.setDisliked(disliked);
            //设置是否收藏
            Favorite favorite = userClients.favorite(videoId, o.getId().toString());
            log.info("是否收藏过该视频: {}", !ObjectUtils.isEmpty(favorite));
            if (!ObjectUtils.isEmpty(favorite)) {
                videoDetailVO.setFavorite(true);
            }
        }

        return videoDetailVO;
    }


    //将list video 转为 list videoVO
    public List<VideoVO> getList(List<Video> videos) {
        //创建VideoVo集合
        List<VideoVO> videoVOS = new ArrayList<>();
        //对video进行遍历 在遍历过程中转为videoVo
        videos.forEach(video -> {
            //video{id title intro cover uid category_id create_at ...}   //videoVo{id title cover category likes uploader create_at}
            VideoVO videoVO = getVideoVO(video);

            videoVOS.add(videoVO);//添加视频
        });
        return videoVOS;
    }


    //将 video 转为 videoVO
    public VideoVO getVideoVO(Video video) {
        //创建VideoVo
        VideoVO videoVO = new VideoVO();
        //复制属性
        BeanUtils.copyProperties(video, videoVO);//复制属性

        //视频服务----->调用用户服务 根据用户id查询用户
        User user = userClients.user(video.getUid().toString());//调用用户服务
        log.info("根据id查询用户信息为: {}", JSONUtils.writeValueAsString(video));
        videoVO.setUploader(user.getName());//设置用户名

        //视频服务---->调用类别服务  根据类别id查询类别
        Category category = categoryClients.category(video.getCategoryId().toString());
        log.info("根据类别id查询到的类别信息为: {}", JSONUtils.writeValueAsString(category));
        videoVO.setCategory(category.getName());

        //设置点赞数量
        videoVO.setLikes(0);

//        String counts = stringRedisTemplate.opsForValue().get(RedisPrefix.VIDEO_LIKE_COUNT_PREFIX + video.getId());
//        if (!StringUtils.isEmpty(counts)) {
//            log.info("当前视频点赞数量为: {}", counts);
//            videoVO.setLikes(Integer.valueOf(counts));
//        }
        return videoVO;
    }

}
