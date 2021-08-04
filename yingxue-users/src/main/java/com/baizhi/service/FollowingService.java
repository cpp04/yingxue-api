package com.baizhi.service;


import com.baizhi.entity.Favorite;
import com.baizhi.entity.Following;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 关注(Following)表服务接口
 *
 * @author chenyn
 * @since 2020-11-12 17:56:35
 */
public interface FollowingService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Following queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Following> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param following 实例对象
     * @return 实例对象
     */
    Following insert(Following following);

    /**
     * 修改数据
     *
     * @param following 实例对象
     * @return 实例对象
     */
    Following update(Following following);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

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