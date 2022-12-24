package com.chainbell.placesearch.place.service.impl;

import com.chainbell.placesearch.place.service.PlaceSearchService;
import org.springframework.stereotype.Service;

@Service
public class PlaceSearchServiceImpl implements PlaceSearchService {


    @Override
    public String getPlaceList(String keyword) {

        // 1. keyword redis queue push

        // 2. open api 호출
        // 2-1. kakao open api

        // 2-2. naver open api

        // 3. 2, 3번 데이터 정리
        // 3-1. 중복 제거
        // 3-2. 정렬(중복 1순위, 카카오 2순위, 네이버 3순위, etc 4순위)


        return null;
    }


}
