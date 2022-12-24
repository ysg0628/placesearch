package com.chainbell.placesearch.place.service;

import java.util.List;

public interface PlaceSearchService {

    /* 검색어 기준의 위치 목록 조회 */
    public List getPlaceList(String keyword);

}
