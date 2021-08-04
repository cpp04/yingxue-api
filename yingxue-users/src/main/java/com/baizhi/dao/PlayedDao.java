package com.baizhi.dao;


import com.baizhi.entity.Played;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 播放历史(Played)表数据库访问层
 *
 * @author chenyn
 * @since 2020-11-12 17:56:35
 */
@Mapper
public interface PlayedDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Played queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Played> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param played 实例对象
     * @return 对象列表
     */
    List<Played> queryAll(Played played);

    /**
     * 新增数据
     *
     * @param played 实例对象
     * @return 影响行数
     */
    int insert(Played played);

    /**
     * 修改数据
     *
     * @param played 实例对象
     * @return 影响行数
     */
    int update(Played played);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 查询播放历史记录
     * @param id 用户id
     * @return 当前用户的播放的历史
     */
    List<Played> queryByUserId(@Param("id") Integer id,@Param("start") Integer start,@Param("rows") Integer rows);


    /**
     * 查询用户是否已经有当前视频播放历史
     */
    Played queryByUserAndVideoId(@Param("id") Integer id,@Param("videoId") Integer videoId);
}
