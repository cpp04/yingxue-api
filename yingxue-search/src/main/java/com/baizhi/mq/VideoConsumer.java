package com.baizhi.mq;

import com.baizhi.vo.VideoVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class VideoConsumer {

    private static final Logger log = LoggerFactory.getLogger(VideoConsumer.class);

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(name = "videos", type = "fanout")
    ))
    public void receive(String message) throws IOException {
        log.info("MQ将接收video信息为: {}", message);
        //1.将mq中video的json格式数据转为一个videoVo对象
        VideoVO videoVO = new ObjectMapper().readValue(message, VideoVO.class);

        //2.创建ES中索引请求对象  参数1:操作索引  参数2:操作类型  参数3:文档id
        IndexRequest indexRequest = new IndexRequest("video", "video", videoVO.getId().toString());
        //3.设置ES文档的内容
        indexRequest.source(message, XContentType.JSON);
        //4.执行索引操作
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        log.info("video信息录入ES的状态为: {}", indexResponse.status());
    }
    //


}
