package com.baizhi.controller;

import com.baizhi.entity.Category;
import com.baizhi.service.CategoryService;
import com.baizhi.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("categories")
public class CategoryController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);


    /**
     * 服务对象
     */
    @Resource
    private CategoryService categoryService;

    /**
     * 分类列表
     */
    @GetMapping
    public List<Category> categories() {
        log.info("进入查询类别列表方法..");
        List<Category> categories = categoryService.findAll();
        log.info("查询当前一级类别列表总数为: {}", categories.size());
        return categories;
    }

    /**
     * 根据id查询类别
     */
    @GetMapping("{id}")
    public Category category(@PathVariable("id") Integer id) {
        log.info("接收到的类别id: {}", id);
        Category category = categoryService.queryById(id);
        log.info("查询到的类别信息: {}", JSONUtils.writeValueAsString(category));
        return category;
    }

}
