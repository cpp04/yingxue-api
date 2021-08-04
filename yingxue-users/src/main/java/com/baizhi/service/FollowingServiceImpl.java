package com.baizhi.service;

import com.baizhi.dao.FollowingDao;
import com.baizhi.entity.Following;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 关注(Following)表服务实现类
 *
 * @author chenyn
 * @since 2020-11-12 17:56:35
 */
@Service("followingService")
@Transactional
public class FollowingServiceImpl implements FollowingService {
    @Resource
    private FollowingDao followingDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Following queryById(Integer id) {
        return this.followingDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Following> queryAllByLimit(int offset, int limit) {
        return this.followingDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param following 实例对象
     * @return 实例对象
     */
    @Override
    public Following insert(Following following) {
        this.followingDao.insert(following);
        return following;
    }

    /**
     * 修改数据
     *
     * @param following 实例对象
     * @return 实例对象
     */
    @Override
    public Following update(Following following) {
        this.followingDao.update(following);
        return this.queryById(following.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.followingDao.deleteById(id) > 0;
    }

    @Override
    public List<Following> findAllByUserId(Integer userId) {
        return this.followingDao.findAllByUserId(userId);
    }

    @Override
    public List<Following> findAllFollowingByUserId(Integer userId) {
        return this.findAllFollowingByUserId(userId);
    }



}