package com.baizhi.controller;

import com.baizhi.entity.Comment;
import com.baizhi.entity.Favorite;
import com.baizhi.entity.User;
import com.baizhi.service.CommentService;
import com.baizhi.service.FavoriteService;
import com.baizhi.service.UserService;
import com.baizhi.utils.JSONUtils;
import com.baizhi.vo.CommentVO;
import com.baizhi.vo.Reviewer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserServiceController {

    private static final Logger log = LoggerFactory.getLogger(UserServiceController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private CommentService commentService;

    //根据用户id返回用户信息服务
    @GetMapping("/userInfo/{id}")
    public User user(@PathVariable("id") String id){
        log.info("接收到用户id: {}",id);
        User user = userService.queryById(Integer.valueOf(id));
        log.info("返回的用户信息: {}", JSONUtils.writeValueAsString(user));
        return user;
    }

    /**
     * 根据用户id视频id查询是否收藏
     * @param videoId
     * @param userId
     * @return
     */
    @GetMapping("/userInfo/favorite")
    public Favorite favorite(@RequestParam("videoId") String videoId,@RequestParam("userId") String userId){
        log.info("接收到的视频id {}, 用户id: {}",videoId,userId);
        Favorite favorite = favoriteService.queryByVideoIdAndUserId(Integer.valueOf(videoId), Integer.valueOf(userId));
        log.info("当前返回的收藏对象是否为空: {}",ObjectUtils.isEmpty(favorite));
        return favorite;
    }


    /**
     * 用户发表评论信息
     */
    @PostMapping("/user/comment/{userId}/{videoId}")
    public void comments(@PathVariable("userId") Integer userId,@PathVariable("videoId") Integer videoId, @RequestBody Comment comment){
        //接受到评论
        log.info("视频id: {}",videoId);
        log.info("评论信息: {}",JSONUtils.writeValueAsString(comment));
        log.info("评论用户信息: {}",userId);
        //设置评论用户信息
        comment.setUid(userId);
        //设置评论视频
        comment.setVideoId(videoId);
        commentService.insert(comment);
    }


    /**
     * 视频评论
     */
    @GetMapping("/user/comments")
    public Map<String,Object> comments(@RequestParam("videoId") Integer videoId,
                                       @RequestParam(value = "page",defaultValue = "1") Integer page,
                                       @RequestParam(value = "per_page",defaultValue = "15") Integer rows){
        log.info("视频id: {}",videoId);
        log.info("当前页: {}",page);
        log.info("每页显示记录数: {}",rows);
        Map<String,Object> result = new HashMap<>();
        Long total_counts = commentService.findByVideoIdCounts(videoId);
        result.put("total_count",total_counts);
        List<CommentVO> commentVOList = new ArrayList<>();
        List<Comment> comments = commentService.findByVideoId(videoId, page, rows);
        comments.forEach(comment->{
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(comment,commentVO);
            Reviewer reviewer = new Reviewer();
            User user = userService.queryById(comment.getUid());
            BeanUtils.copyProperties(user,reviewer);
            commentVO.setReviewer(reviewer);
            commentVOList.add(commentVO);
        });
        result.put("items",commentVOList);
        return result;
    }


}
