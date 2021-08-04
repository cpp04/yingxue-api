package com.baizhi.feignclients;

import com.baizhi.entity.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("API-CATEGORIES")
public interface CategoryClients {

    //根据类别id获取类别信息
    @GetMapping("/categories/{id}")
    public Category category(@PathVariable("id") String id);
}
