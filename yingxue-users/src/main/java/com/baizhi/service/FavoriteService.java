package com.baizhi.service;


import com.baizhi.entity.Favorite;
import com.baizhi.vo.VideoVO;

import java.util.List;

/**
 * 收藏(Favorite)表服务接口
 *
 * @author chenyn
 * @since 2020-11-12 17:56:35
 */
public interface FavoriteService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Favorite queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Favorite> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param favorite 实例对象
     * @return 实例对象
     */
    Favorite insert(Favorite favorite);

    /**
     * 修改数据
     *
     * @param favorite 实例对象
     * @return 实例对象
     */
    Favorite update(Favorite favorite);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 取消收藏
     * @param videoId
     */
    int deleteByVideoIdAndUserId(Integer videoId, Integer userId);

    /**
     * 判断当前用户是否收藏过该视频
     * @param videoId
     * @param id
     * @return
     */
    Favorite queryByVideoIdAndUserId(Integer videoId, Integer id);

    /**
     * 我的收藏
     */
    List<VideoVO> findFavoritesByUserId(Integer userId);




}