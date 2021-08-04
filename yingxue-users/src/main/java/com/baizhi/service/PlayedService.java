package com.baizhi.service;

import com.baizhi.entity.Played;
import com.baizhi.vo.VideoVO;

import java.util.List;

/**
 * 播放历史(Played)表服务接口
 *
 * @author chenyn
 * @since 2020-11-12 17:56:35
 */
public interface PlayedService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Played queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Played> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param played 实例对象
     * @return 实例对象
     */
    Played insert(Played played);

    /**
     * 修改数据
     *
     * @param played 实例对象
     * @return 实例对象
     */
    Played update(Played played);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);


    /**
     * 根据用户id分页查询
     */
    List<VideoVO> queryByUserId(Integer userId,Integer page,Integer rows);

}