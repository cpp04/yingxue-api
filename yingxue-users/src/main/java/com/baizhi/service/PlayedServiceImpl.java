package com.baizhi.service;


import com.baizhi.dao.PlayedDao;
import com.baizhi.entity.Played;
import com.baizhi.feignclients.VideosClient;
import com.baizhi.vo.VideoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 播放历史(Played)表服务实现类
 *
 * @author chenyn
 * @since 2020-11-12 17:56:35
 */
@Service("playedService")
@Transactional
public class PlayedServiceImpl implements PlayedService {
    @Resource
    private PlayedDao playedDao;

    @Autowired
    private VideosClient videosClient;



    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Played queryById(Integer id) {
        return this.playedDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Played> queryAllByLimit(int offset, int limit) {
        return this.playedDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param played 实例对象
     * @return 实例对象
     */
    @Override
    public Played insert(Played played) {
        //1.根据用户id  视频id 判断是否存在当前记录播放历史
        Played playedOld = this.playedDao.queryByUserAndVideoId(played.getUid(), played.getVideoId());
        if(ObjectUtils.isEmpty(playedOld)){
            //2.不存在则添加历史信息
            played.setCreatedAt(new Date());
            played.setUpdatedAt(new Date());
            this.playedDao.insert(played);
            return played;
        }else{
            //3.存在更新播放历史时间即可
            playedOld.setUpdatedAt(new Date());
            this.playedDao.update(playedOld);
            return playedOld;
        }
    }

    /**
     * 修改数据
     *
     * @param played 实例对象
     * @return 实例对象
     */
    @Override
    public Played update(Played played) {
        this.playedDao.update(played);
        return this.queryById(played.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.playedDao.deleteById(id) > 0;
    }


    @Override
    public List<VideoVO> queryByUserId(Integer userId, Integer page, Integer rows) {
        int start = (page -1)*rows;
        //播放历史列表
        List<Played> playeds = playedDao.queryByUserId(userId, start, rows);
        List<Integer> ids = new ArrayList<>();
        playeds.forEach(played->ids.add(played.getVideoId()));
        return videosClient.getVideos(ids);
    }
}