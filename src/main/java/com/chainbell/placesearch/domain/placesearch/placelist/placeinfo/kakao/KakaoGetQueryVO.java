package com.chainbell.placesearch.domain.placesearch.placelist.placeinfo.kakao;

import lombok.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Setter
@Getter
@ToString
@Builder
public class KakaoGetQueryVO {

    @NonNull
    private String query;

    public String getQueryString() {
        return "query="+URLEncoder.encode(this.query, StandardCharsets.UTF_8);
    }

}
