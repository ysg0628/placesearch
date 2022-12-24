package com.chainbell.placesearch.domain.placesearch;

import com.chainbell.placesearch.domain.placesearch.placelist.PlaceListVO;
import com.chainbell.placesearch.domain.placesearch.placelist.kakao.KakaoGetQueryVO;
import com.chainbell.placesearch.domain.placesearch.placelist.naver.NaverGetQueryVO;
import com.chainbell.placesearch.domain.placesearch.placelist.naver.NaverHeaderVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;


/**
 * place search domain root
 * */
@Setter
@Getter
@ToString
@Builder
public class PlaceSearchVO {

    private String searchKeyword;

    private PlaceListVO placeList;

    public String getKakaoGetQueryString(){
        KakaoGetQueryVO kakaoGetQueryVO = KakaoGetQueryVO.builder().query(this.searchKeyword).build();
        return kakaoGetQueryVO.getQueryString();
    }

    public String getNaverGetQueryString(){
        NaverGetQueryVO naverGetQueryVO = NaverGetQueryVO.builder().query(this.searchKeyword).build();
        return naverGetQueryVO.getQueryString();
    }

    public Map getNaverHeaderInfoFormat(String clientId, String clientSecret){
        NaverHeaderVO naverHeaderVO = NaverHeaderVO.builder().clientId(clientId).clientSecret(clientSecret).build();
        return naverHeaderVO.getNaverOpenApiHeader();
    }

}
