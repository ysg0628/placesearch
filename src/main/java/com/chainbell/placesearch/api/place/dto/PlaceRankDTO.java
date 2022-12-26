package com.chainbell.placesearch.api.place.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class PlaceRankDTO {

    // 호출 횟수
    private int scoreRank;

    // 키워드
    private String keyword;

}
