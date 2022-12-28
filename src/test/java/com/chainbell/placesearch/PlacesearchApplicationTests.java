package com.chainbell.placesearch;

import com.chainbell.placesearch.common.util.HttpUtil;
import com.chainbell.placesearch.domain.placesearch.PlaceSearchVO;
import com.chainbell.placesearch.domain.placesearch.placelist.PlaceListVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;

@SpringBootTest
class PlacesearchApplicationTests {

    @Value("${kakao.openapi.host}")
    String kakaoHost;

    @Value("${kakao.openapi.url}")
    String kakaoUrl;

    @Value("${kakao.openapi.restapi.key}")
    String kakaoKey;

    @Value("${naver.openapi.host}")
    String naverHost;

    @Value("${naver.openapi.url}")
    String naverUrl;

    @Value("${naver.openapi.header.client.id}")
    String naverId;

    @Value("${naver.openapi.header.client.secret}")
    String naverSecret;

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    void contextLoads() throws JSONException, IOException {


    }

}
