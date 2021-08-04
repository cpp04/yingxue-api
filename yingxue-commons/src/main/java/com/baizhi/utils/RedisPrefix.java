package com.baizhi.utils;

public interface RedisPrefix {

    String USER_LIKE_PREFIX= "USER_LIKE_";  //用户喜欢视频列表前缀
    String USER_DISLIKE_PREFIX= "USER_DISLIKE_";//用户不喜欢的列表
    String VIDEO_LIKE_COUNT_PREFIX = "VIDEO_LIKE_COUNT_";//用户喜欢视频数量前缀
    String VIDEO_PLAYED_COUNT_PREFIX = "VIDEO_PLAYED_COUNT_";//视频播放次数前缀

}
