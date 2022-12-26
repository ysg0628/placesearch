package com.chainbell.placesearch.schedule;

import com.chainbell.placesearch.helper.redis.PlaceSearchKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PlaceSearchSchedule {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${search.keyword.count.pop}")
    private long countPop;

    @Scheduled(fixedDelay = 1000)
    public void keywordRankingSchedule() {

        // 1. redis queue keyword lpop
        List<Object> keywordList = new ArrayList<Object>();
        try {
            // 1-1. get list of values from redis by pipelining
            keywordList = redisTemplate.executePipelined(
                    (RedisCallback<Object>) connection -> {
                        for (int i = 0; i < countPop; i++) {
                            connection.listCommands().lPop(PlaceSearchKey.keywordQueue.getBytes());
                        }
                        return null;
                    });

            // 1-2. remove null value from pipelined result
            while (keywordList.remove(null)) ;

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. redis keyword 중복 카운트 계산
        Map<String, Integer> scoreCount = new HashMap<String, Integer>();
        for (Object keywordObj : keywordList) {
            String keywordTemp = keywordObj.toString();
            System.out.println("   " + keywordTemp);
            if(scoreCount.containsKey(keywordTemp)){
                scoreCount.put(keywordTemp, scoreCount.get(keywordTemp)+1);
            }
            else{
                scoreCount.put(keywordTemp, 1);
            }

        }

        // 3. 2번 조회값 + 1 -> redis keyword sorted set 저장
        redisTemplate.executePipelined(
                (RedisCallback<Object>) connection -> {
                    for (String key : scoreCount.keySet()) {
                        connection.zSetCommands().zIncrBy(PlaceSearchKey.keywordRank.getBytes(), Double.parseDouble(scoreCount.get(key).toString()), key.getBytes());
                    }
                    return null;
                });
    }

}
