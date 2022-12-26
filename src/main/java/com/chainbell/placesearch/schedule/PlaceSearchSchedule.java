package com.chainbell.placesearch.schedule;

import com.chainbell.placesearch.helper.redis.PlaceSearchKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlaceSearchSchedule {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${search.keyword.pop.count}")
    private long popCount;

    @Scheduled(fixedDelay = 1000)
    public void keywordRankingSchedule() {

        // 1. redis queue keyword lpop
        List<Object> keywordList = new ArrayList<Object>();
        try {
            // 1-1. set redis key list for pipelining
            List<String> redisKeyList = new ArrayList<String>();
            for (int i = 0; i < popCount; i++)
                redisKeyList.add(PlaceSearchKey.getKeywordQueue);

            // 1-2. get list of values from redis by pipelining
            keywordList = redisTemplate.executePipelined(
                    (RedisCallback<Object>) connection -> {
                        for (String key : redisKeyList) {
                            connection.listCommands().lPop(key.getBytes());
                        }
                        return null;
                    });

            // 1-3. remove null value from pipelined result
            while (keywordList.remove(null)) ;

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. redis keyword 조회

        // 3. 2번 조회값 + 1 -> redis keyword sorted set 저장

    }

}
