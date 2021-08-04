package com.baizhi.service;

import com.baizhi.entity.Video;
import com.baizhi.vo.VideoDetailVO;
import com.baizhi.vo.VideoVO;

import java.util.List;

/**
 * 视频(Video)表服务接口
 *
 * @author chenyn
 * @since 2020-11-12 17:56:35
 */
public interface VideoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    VideoVO queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<VideoVO> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param video 实例对象
     * @return 实例对象
     */
    Video insert(Video video);

    /**
     * 修改数据
     *
     * @param video 实例对象
     * @return 实例对象
     */
    Video update(Video video);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 查询总条数
     *
     * @return 总条数
     */
    Long findTotalCounts();


//    /**
//     * 前台接口- 推荐视频
//     * @param page 当前页
//     * @param rows 每页显示记录数
//     * @return 返回推荐列表
//     */
//    List<VideoVO> queryAllRecommendsByLimit(Integer page, Integer rows);
//
//    /**
//     * 前台接口- 根据用户id查询用户视频
//     * @param id
//     * @return
//     */
//    List<VideoVO> findAllByUserId(Integer id);
//

    /**
     * 前台接口- 根据类别查询视频列表
     *
     * @param page       当前页
     * @param rows       每页展示记录数
     * @param categoryId 类别id
     * @return 返回符合条件的视频
     */
    List<VideoVO> findAllByCategoryId(Integer page, Integer rows, Integer categoryId);

    /**
     * 根据视频id查询视频详细
     *
     * @param videoId
     * @param token
     * @return
     */
    VideoDetailVO queryDetailById(String videoId, String token);
//
//    /**
//     * 前台接口- 用户播放历史
//     * @param id
//     * @return
//     */
//    List<VideoVO> queryPlayedByUserId(Integer id);
//
//    /**
//     * 前台接口- 用户收藏
//     * @param id 用户id
//     * @return 返回收藏视频列表
//     */
//    List<VideoVO> queryFavoritesByUserId(Integer id);
//

    /**
     *
     * @return
     */
    // HashMap<String, Object> search(Integer page, Integer rows, String q);
}