package com.chainbell.placesearch.api.place.service.impl;

import com.chainbell.placesearch.api.place.dto.PlaceListDTO;
import com.chainbell.placesearch.api.place.dto.PlaceRankDTO;
import com.chainbell.placesearch.common.util.HttpUtil;
import com.chainbell.placesearch.domain.placesearch.PlaceSearchVO;
import com.chainbell.placesearch.domain.placesearch.placelist.PlaceListVO;
import com.chainbell.placesearch.domain.placesearch.placelist.placeinfo.PlaceInfoVO;
import com.chainbell.placesearch.helper.redis.PlaceSearchKey;
import com.chainbell.placesearch.api.place.service.PlaceSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.zset.Tuple;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlaceSearchServiceImpl implements PlaceSearchService {

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

    @Value("${search.keyword.count.pop}")
    private long countPop;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public List<PlaceListDTO> getPlaceList(String keyword) {

        // 1. keyword redis queue push
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        listOperations.rightPush(PlaceSearchKey.keywordQueue, keyword);

        // 2. open api 호출 - Domain Root 선언
        PlaceSearchVO placeSearch = PlaceSearchVO.builder()
                .searchKeyword(keyword)
                .placeList(new PlaceListVO())
                .build();

        // 2-1. kakao open api (default 45개)
        try {
            String kakaoSearchResult = HttpUtil.getRequest(kakaoHost + kakaoUrl, placeSearch.getKakaoGetQueryString(), kakaoKey, null);
            placeSearch.getPlaceList().setKakaoPlaceInfoVOList(kakaoSearchResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2-2. naver open api (default 1, max 5개)
        try {
            String naverSearchResult = HttpUtil.getRequest(naverHost + naverUrl, placeSearch.getNaverGetQueryString(), kakaoKey, placeSearch.getNaverHeaderInfoFormat(naverId, naverSecret));
            placeSearch.getPlaceList().setNaverPlaceInfoVOList(naverSearchResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3. 2, 3번 데이터 정리
        List<PlaceListDTO> placeList = new ArrayList<PlaceListDTO>();
        List<PlaceInfoVO> placeInfoList = placeSearch.getPlaceList().getPlaceInfoList();
        for (PlaceInfoVO placeInfoVO : placeInfoList) {
            placeList.add(PlaceListDTO.builder()
                    .name(placeInfoVO.getName())
                    .roadAddress(placeInfoVO.getRoadAddress())
                    .address(placeInfoVO.getAddress())
                    .build());
        }

        return placeList;
    }

    @Override
    public List<PlaceRankDTO> getPopularPlaceKeywordList() {
        List<PlaceRankDTO> keywordInfoList = new ArrayList<PlaceRankDTO>();
        try {
            Set<ZSetOperations.TypedTuple<String>> keywordInfoSet = redisTemplate.opsForZSet().reverseRangeWithScores(PlaceSearchKey.keywordRank, 0, 9);
            if (keywordInfoSet != null && keywordInfoSet.size() > 0) {
                for (ZSetOperations.TypedTuple<String> keywordInfo : keywordInfoSet) {
                    PlaceRankDTO paramTemp = PlaceRankDTO.builder()
                            .keyword(keywordInfo.getValue())
                            .scoreRank(keywordInfo.getScore().intValue())
                            .build();
                    keywordInfoList.add(paramTemp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            keywordInfoList = new ArrayList<PlaceRankDTO>();
        }

        return keywordInfoList;
    }

    @Override
    public void setPlaceSearchKeywordRank() {

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
            if (scoreCount.containsKey(keywordTemp))
                scoreCount.put(keywordTemp, scoreCount.get(keywordTemp) + 1);
            else
                scoreCount.put(keywordTemp, 1);
        }

        // 3. 2번 조회값 + 1 -> redis keyword sorted set 저장
        try {
            redisTemplate.executePipelined(
                    (RedisCallback<Object>) connection -> {
                        for (String key : scoreCount.keySet()) {
                            connection.zSetCommands().zIncrBy(PlaceSearchKey.keywordRank.getBytes(), Double.parseDouble(scoreCount.get(key).toString()), key.getBytes());
                        }
                        return null;
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
