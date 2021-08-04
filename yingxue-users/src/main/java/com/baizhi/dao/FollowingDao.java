package com.baizhi.dao;


import com.baizhi.entity.Following;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 关注(Following)表数据库访问层
 *
 * @author chenyn
 * @since 2020-11-12 17:56:35
 */
@Mapper
public interface FollowingDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Following queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Following> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param following 实例对象
     * @return 对象列表
     */
    List<Following> queryAll(Following following);

    /**
     * 新增数据
     *
     * @param following 实例对象
     * @return 影响行数
     */
    int insert(Following following);

    /**
     * 修改数据
     *
     * @param following 实例对象
     * @return 影响行数
     */
    int update(Following following);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 我的粉丝
     * @param userId 用户id
     * @return 返回粉丝列表
     */
    List<Following> findAllByUserId(Integer userId);


    /**
     * 我的关注
     * @param userId 用户id
     * @return 返回关注列表
     */
    List<Following> findAllFollowingByUserId(Integer userId);

}