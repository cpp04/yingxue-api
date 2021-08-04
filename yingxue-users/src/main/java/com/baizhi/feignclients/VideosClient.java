package com.baizhi.feignclients;

import com.baizhi.entity.Video;
import com.baizhi.vo.VideoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("API-VIDEOS")
public interface VideosClient {

    @PostMapping("publish")
    Video publish(@RequestBody Video video);

    @GetMapping("getVideos")
    List<VideoVO> getVideos(@RequestParam("ids") List<Integer> ids);
}
