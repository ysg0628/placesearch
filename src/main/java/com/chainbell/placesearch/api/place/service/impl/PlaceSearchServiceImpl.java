package com.chainbell.placesearch.api.place.service.impl;

import com.chainbell.placesearch.api.place.dto.PlaceRankDTO;
import com.chainbell.placesearch.common.util.HttpUtil;
import com.chainbell.placesearch.domain.placesearch.PlaceSearchVO;
import com.chainbell.placesearch.domain.placesearch.placelist.PlaceListVO;
import com.chainbell.placesearch.helper.redis.PlaceSearchKey;
import com.chainbell.placesearch.api.place.service.PlaceSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.zset.Tuple;
import org.springframework.data.redis.core.ListOperations;
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

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public List getPlaceList(String keyword) {

        System.out.println("getPlaceList " + keyword);

        // 1. keyword redis queue push
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        listOperations.rightPush(PlaceSearchKey.keywordQueue, keyword);

        // 2. open api 호출 - Domain Root 선언
        PlaceSearchVO placeSearch = PlaceSearchVO.builder()
                .searchKeyword(keyword)
                .placeList(new PlaceListVO())
                .build();

        // 2-1. kakao open api (default 45개)
        try{
            String kakaoSearchResult = HttpUtil.getRequest(kakaoHost+kakaoUrl, placeSearch.getKakaoGetQueryString(), kakaoKey, null);
            placeSearch.getPlaceList().setKakaoPlaceInfoVOList(kakaoSearchResult);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        // 2-2. naver open api (default 1, max 5개)
        try{
            String naverSearchResult = HttpUtil.getRequest(naverHost+naverUrl, placeSearch.getNaverGetQueryString(), kakaoKey, placeSearch.getNaverHeaderInfoFormat(naverId, naverSecret));
            placeSearch.getPlaceList().setNaverPlaceInfoVOList(naverSearchResult);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        // 3. 2, 3번 데이터 정리
        List result = placeSearch.getPlaceList().getPlaceInfoList();

        return result;
    }

    @Override
    public List<PlaceRankDTO> getPopularPlaceKeywordList() {
        List<PlaceRankDTO> keywordInfoList = new ArrayList<PlaceRankDTO>();
        try{
            Set<ZSetOperations.TypedTuple<String>> keywordInfoSet = redisTemplate.opsForZSet().reverseRangeWithScores(PlaceSearchKey.keywordRank, 0, 9);
            if(keywordInfoSet != null && keywordInfoSet.size() > 0){
                for(ZSetOperations.TypedTuple<String> keywordInfo : keywordInfoSet){
                    PlaceRankDTO paramTemp = PlaceRankDTO.builder()
                            .keyword(keywordInfo.getValue())
                            .scoreRank(keywordInfo.getScore().intValue())
                            .build();
                    keywordInfoList.add(paramTemp);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            keywordInfoList = new ArrayList<PlaceRankDTO>();
        }

        return keywordInfoList;
    }


}
