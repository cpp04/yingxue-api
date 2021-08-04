package com.baizhi.controller;

import com.baizhi.entity.Video;
import com.baizhi.service.VideoService;
import com.baizhi.utils.JSONUtils;
import com.baizhi.vo.VideoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class VideoServiceController {

    private static final Logger log = LoggerFactory.getLogger(VideoServiceController.class);




    @Autowired
    private VideoService videoService;


    /**
     * 保存视频
     */
    @PostMapping("publish")
    public Video publish(@RequestBody Video video){
        log.info("接收到的video: {}", JSONUtils.writeValueAsString(video));
        return videoService.insert(video);
    }

    /**
     * 根据视频id查询视频的方法
     */
    @GetMapping("getVideos")
    public List<VideoVO> getVideos(@RequestParam("ids") List<Integer> ids){
        List<VideoVO> videoVOS = new ArrayList<>();
        log.info("ids: {}",ids);
        ids.forEach(id->{
            VideoVO videoVO = videoService.queryById(id);
            videoVOS.add(videoVO);
        });
        return videoVOS;
    }

}