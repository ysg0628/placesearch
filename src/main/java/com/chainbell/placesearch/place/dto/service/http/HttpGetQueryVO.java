package com.chainbell.placesearch.place.dto.service.http;

import lombok.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Setter
@Getter
@ToString
@Builder
public class HttpGetQueryVO {

    @NonNull
    private String query;

    public String getQueryString() {
        return "query="+URLEncoder.encode(this.query, StandardCharsets.UTF_8);
    }

}
