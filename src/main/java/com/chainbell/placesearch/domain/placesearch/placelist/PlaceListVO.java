package com.chainbell.placesearch.domain.placesearch.placelist;

import com.chainbell.placesearch.domain.placesearch.placelist.kakao.KakaoPlaceInfoVO;
import com.chainbell.placesearch.domain.placesearch.placelist.naver.NaverPlaceInfoVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class PlaceListVO {
    List<KakaoPlaceInfoVO> kakaoPlaceInfoVOList = new ArrayList<KakaoPlaceInfoVO>();

    List<NaverPlaceInfoVO> naverPlaceInfoVOList = new ArrayList<NaverPlaceInfoVO>();

    public void setKakaoPlaceInfoVOList(String kakaoPlaceJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            JSONObject kakaoResult = new JSONObject(kakaoPlaceJson);
            for (int i = 0; i < kakaoResult.getJSONArray("documents").length(); i++) {
                if(i == 5){
                    break;
                }
                kakaoPlaceInfoVOList.add(mapper.readValue(kakaoResult.getJSONArray("documents").get(i).toString(), KakaoPlaceInfoVO.class));
            }
        } catch (Exception e) {
            kakaoPlaceInfoVOList = new ArrayList<KakaoPlaceInfoVO>();
        }

    }

    public void setNaverPlaceInfoVOList(String naverPlaceJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            JSONObject naverResult = new JSONObject(naverPlaceJson);
            for (int i = 0; i < naverResult.getJSONArray("items").length(); i++) {
                naverPlaceInfoVOList.add(mapper.readValue(naverResult.getJSONArray("items").get(i).toString(), NaverPlaceInfoVO.class));
                naverPlaceInfoVOList.get(i).setTitle(
                        naverPlaceInfoVOList.get(i).getTitle().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "")
                );
            }
        } catch (Exception e) {
            naverPlaceInfoVOList = new ArrayList<NaverPlaceInfoVO>();
        }


    }

    public String getPlaceInfoList(){
        // 중복 제거
        // 정렬(중복 1순위, 카카오 2순위, 네이버 3순위, etc 4순위)
        


        return "";
    }

}
