package com.chainbell.placesearch.api.place.service;

import com.chainbell.placesearch.api.place.dto.PlaceRankDTO;

import java.util.List;

public interface PlaceSearchService {

    /* 검색어 기준의 위치 목록 조회 */
    List getPlaceList(String keyword);

    /* 검색 횟수 기준 상위 키워드 조회 */
    List<PlaceRankDTO> getPopularPlaceKeywordList();

    void setPlaceSearchKeywordRank();

}
