package com.chainbell.placesearch.place.service.impl;

import com.chainbell.placesearch.common.util.HttpUtil;
import com.chainbell.placesearch.domain.placesearch.PlaceSearchVO;
import com.chainbell.placesearch.domain.placesearch.placelist.PlaceListVO;
import com.chainbell.placesearch.place.service.PlaceSearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    @Override
    public String getPlaceList(String keyword) {

        // 1. keyword redis queue push

        // 2. open api 호출 - Domain Root 선언
        PlaceSearchVO placeSearch = PlaceSearchVO.builder()
                .searchKeyword(keyword)
                .placeList(new PlaceListVO())
                .build();

        // 2-1. kakao open api
        try{
            String kakaoSearchResult = HttpUtil.getRequest(kakaoHost+kakaoUrl, placeSearch.getKakaoGetQueryString(), kakaoKey, null);
            placeSearch.getPlaceList().setKakaoPlaceInfoVOList(kakaoSearchResult);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        // 2-2. naver open api
        try{
            String naverSearchResult = HttpUtil.getRequest(naverHost+naverUrl, placeSearch.getNaverGetQueryString(), kakaoKey, placeSearch.getNaverHeaderInfoFormat(naverId, naverSecret));
            placeSearch.getPlaceList().setNaverPlaceInfoVOList(naverSearchResult);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        // 3. 2, 3번 데이터 정리


        return null;
    }


}
