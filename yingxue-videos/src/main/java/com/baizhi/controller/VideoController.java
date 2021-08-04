package com.baizhi.controller;

import com.baizhi.entity.Comment;
import com.baizhi.entity.User;
import com.baizhi.feignclients.UserClients;
import com.baizhi.service.VideoService;
import com.baizhi.utils.JSONUtils;
import com.baizhi.vo.VideoDetailVO;
import com.baizhi.vo.VideoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class VideoController {

    private static final Logger log = LoggerFactory.getLogger(VideoController.class);

    @Autowired
    private VideoService videoService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserClients userClients;


    /**
     * 评论信息
     */
    @GetMapping("/videos/{videoId}/comments")
    public Map<String, Object> comments(
            @PathVariable("videoId") Integer videoId,
            @RequestParam("page") Integer page,
            @RequestParam("per_page") Integer rows, HttpServletRequest request) {
        log.info("视频id: {}", videoId);
        log.info("当前页: {}", page);
        log.info("每页显示记录数: {}", rows);
        return userClients.comments(videoId, page, rows);
    }


    /**
     * 视频评论
     */
    @PostMapping("/videos/{videoId}/comments")
    public void comments(@PathVariable("videoId") Integer videoId, @RequestBody Comment comment, HttpServletRequest request) {
        //1.获取token
        String token = request.getParameter("token");
        //2.根据tokenKey获取用户信息
        String tokenKey = "session_" + token;
        //3.获取redis登陆用户信息
        User user = (User) redisTemplate.opsForValue().get(tokenKey);

        log.info("token为: {}", token);
        log.info("评论的视频id: " + videoId);
        log.info("评论对象内容: " + JSONUtils.writeValueAsString(comment));
        log.info("当前评论的用户: " + JSONUtils.writeValueAsString(user));

        //4.调用用户服务视频评论功能
        userClients.comments(user.getId(), videoId, comment);
    }

    /**
     * 查看分类下视频列表
     */
    @GetMapping("/videos")
    public List<VideoVO> videos(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                @RequestParam(value = "rows", defaultValue = "5") Integer rows,
                                @RequestParam("category") Integer categoryId) {
        log.info("当前页:{},每页显示记录数:{},分类id:{}", page, rows, categoryId);
        List<VideoVO> videoVOS = videoService.findAllByCategoryId(page, rows, categoryId);
        log.info("复合条件的记录总数: {}", videoVOS.size());
        return videoVOS;
    }


    /**
     * 查询视频详情
     */

    @GetMapping("/videos/{id}")
    public VideoDetailVO video(@PathVariable("id") String videoId, String token) {
        log.info("当前接收到的videoId: {}", videoId);
        VideoDetailVO videoDetailVO = videoService.queryDetailById(videoId, token);
        log.info("查询到的视频详细对象信息: {}", JSONUtils.writeValueAsString(videoDetailVO));
        return videoDetailVO;
    }


    /**
     * 视频推荐
     */
    @GetMapping("recommends") //video
    public List<VideoVO> recommends(@RequestParam("page") Integer page, @RequestParam("per_page") Integer per_page) {
        List<VideoVO> videoVOS = videoService.queryAllByLimit(page, per_page);
        log.info("视频推荐列表数量: {}", videoVOS.size());
        return videoVOS;
    }


    //测试demo
    @GetMapping("demo")
    public String demo() {
        log.info("video demo is ok!!!");
        return "video ok !";
    }


}