package com.chainbell.placesearch.domain.placesearch.placelist.placeinfo.naver;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class NaverPlaceInfoVO {

    @JsonProperty("title")
    String title;

    @JsonProperty("link")
    String link;

    @JsonProperty("category")
    String category;

    @JsonProperty("description")
    String description;

    @JsonProperty("telephone")
    String telephone;

    @JsonProperty("address")
    String address;

    @JsonProperty("roadAddress")
    String roadAddress;

    @JsonProperty("mapx")
    String mapx;

    @JsonProperty("mapy")
    String mapy;

}
