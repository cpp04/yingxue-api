package com.baizhi.service;

import com.baizhi.vo.VideoVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class VideoSearchServiceImpl implements VideoSearchService {


    private static final Logger log = LoggerFactory.getLogger(VideoSearchServiceImpl.class);
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public Map<String, Object> videos(String q, Integer page, Integer rows) {
        //定义map  total_count 返回查询总数        items: 返回查询记录
        Map<String, Object> result = new HashMap<>();

        //1.计算分页 起始为0
        int start = (page - 1) * rows;
        //2.创建搜索对象
        SearchRequest searchRequest = new SearchRequest();

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder
                .from(start)  //从start开始
                .size(rows)   //每页显示rows
                .query(QueryBuilders.termQuery("title", q));//term查询

        //3.设置搜索索引 video  设置搜索类型video  设置搜索条件
        searchRequest.indices("video").types("video").source(sourceBuilder);

        SearchResponse search = null;
        try {
            //4.执行搜索
            search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            //5.获取符合条件总数
            long totalHits = search.getHits().totalHits;
            //6.封装结果
            if (totalHits > 0) {
                result.put("total_count", totalHits);//设置总条数
                //7.获取符合条件文档数组
                SearchHit[] hits = search.getHits().getHits();
                //8.创建list集合
                List<VideoVO> videoVOS = new ArrayList<>();
                //9.遍历符合条件每一个文档封装成videoVO对象
                for (SearchHit hit : hits) {
                    //10.获取文件字符串表现形式  就是json格式
                    String sourceAsString = hit.getSourceAsString();
                    log.info("符合条件的结果: {}", sourceAsString);
                    //11.通过jackson将json格式转为videoVo对象
                    VideoVO videoVO = new ObjectMapper().readValue(sourceAsString, VideoVO.class);
                    //12.设置videoVo文档id
                    videoVO.setId(Integer.parseInt(hit.getId()));
                    //13.放入videoVo集合
                    videoVOS.add(videoVO);
                }
                result.put("items", videoVOS);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
