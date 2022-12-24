package com.chainbell.placesearch.domain.placesearch.placelist.naver;

import lombok.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Setter
@Getter
@ToString
@Builder
public class NaverGetQueryVO {

    @NonNull
    private String query;

//    private int display = 5;

    public String getQueryString() {
        return "query="+ URLEncoder.encode(this.query, StandardCharsets.UTF_8)
                +"&display=5";
    }

}
