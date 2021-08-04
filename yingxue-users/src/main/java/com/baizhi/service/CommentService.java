package com.baizhi.service;


import com.baizhi.entity.Comment;

import java.util.List;

/**
 * 评论(Comment)表服务接口
 *
 * @author makejava
 * @since 2020-12-01 10:26:16
 */
public interface CommentService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Comment queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Comment> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param comment 实例对象
     * @return 实例对象
     */
    Comment insert(Comment comment);

    /**
     * 修改数据
     *
     * @param comment 实例对象
     * @return 实例对象
     */
    Comment update(Comment comment);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);


    /**
     * 根据视频id查询所有评论
     * @param id
     * @return
     */
    List<Comment> findByVideoId(Integer id,Integer page,Integer rows);


    /**
     * 根据视频id查询总条数
     */
    Long findByVideoIdCounts(Integer id);
}