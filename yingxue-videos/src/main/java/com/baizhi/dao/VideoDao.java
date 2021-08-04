package com.baizhi.dao;

import com.baizhi.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 视频(Video)表数据库访问层
 *
 * @author chenyn
 * @since 2020-11-12 17:56:35
 */
@Mapper
public interface VideoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Video queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Video> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param video 实例对象
     * @return 对象列表
     */
    List<Video> queryAll(Video video);

    /**
     * 新增数据
     *
     * @param video 实例对象
     * @return 影响行数
     */
    int insert(Video video);

    /**
     * 修改数据
     *
     * @param video 实例对象
     * @return 影响行数
     */
    int update(Video video);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 查询总条数
     * @return 总条数
     */
    Long findTotalCounts();


    /**
     * 根据用户id查询上传视频
     * @param id
     * @return
     */
    List<Video> findAllByUserId(Integer id);

    /**
     * 根据类别id查询
     * @param offset 当前页
     * @param limit 每页显示记录数
     * @param categoryId 类别id
     * @return
     */
    List<Video> findAllByCategoryId(@Param("offset") Integer offset, @Param("limit") Integer limit, @Param("categoryId") Integer categoryId);
}