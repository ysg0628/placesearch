package com.chainbell.placesearch.schedule;

import com.chainbell.placesearch.api.place.service.PlaceSearchService;
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
    PlaceSearchService placeSearchService;

    @Scheduled(fixedDelay = 1000)
    public void keywordRankingSchedule() {
        placeSearchService.setPlaceSearchKeywordRank();
    }

}
