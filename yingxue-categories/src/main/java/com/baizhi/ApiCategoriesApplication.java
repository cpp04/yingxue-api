package com.baizhi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiCategoriesApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiCategoriesApplication.class,args);
    }
}
