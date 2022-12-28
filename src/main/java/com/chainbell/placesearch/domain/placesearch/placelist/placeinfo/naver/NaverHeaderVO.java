package com.chainbell.placesearch.domain.placesearch.placelist.placeinfo.naver;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@ToString
@Builder
public class NaverHeaderVO {

    private final String clientIdHeaderKey = "X-Naver-Client-Id";
    private String clientId;

    private final String clientSecretHeaderKey = "X-Naver-Client-Secret";
    private String clientSecret;

    public Map<String, String> getNaverOpenApiHeader(){
        Map<String, String> headerInfo = new HashMap<String, String>();
        headerInfo.put(clientIdHeaderKey, this.clientId);
        headerInfo.put(clientSecretHeaderKey, this.clientSecret);
        return headerInfo;
    }


}
