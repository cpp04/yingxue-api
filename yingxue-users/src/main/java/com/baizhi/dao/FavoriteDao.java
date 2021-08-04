package com.baizhi.dao;


import com.baizhi.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 收藏(Favorite)表数据库访问层
 *
 * @author chenyn
 * @since 2020-11-12 17:56:35
 */
@Mapper
public interface FavoriteDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Favorite queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Favorite> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param favorite 实例对象
     * @return 对象列表
     */
    List<Favorite> queryAll(Favorite favorite);

    /**
     * 新增数据
     *
     * @param favorite 实例对象
     * @return 影响行数
     */
    int insert(Favorite favorite);

    /**
     * 修改数据
     *
     * @param favorite 实例对象
     * @return 影响行数
     */
    int update(Favorite favorite);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 我的收藏
     * @param id
     * @return
     */
    List<Favorite> queryByUserId(Integer id);

    /**
     * 取消收藏
     * @param videoId
     */
    int deleteByVideoIdAndUserId(@Param("videoId") Integer videoId, @Param("userId") Integer userId);

    /**
     * 判断是否收藏过该视频
     * @param videoId
     * @param userId
     * @return
     */
    Favorite queryByVideoIdAndUserId(@Param("videoId") Integer videoId,@Param("userId") Integer userId);
}