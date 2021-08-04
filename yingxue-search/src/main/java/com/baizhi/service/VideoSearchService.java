package com.baizhi.service;

import java.util.Map;


public interface VideoSearchService {


    Map<String,Object> videos(String q,Integer page,Integer rows);
}
