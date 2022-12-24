package com.chainbell.placesearch.domain.placesearch.placelist;

import com.chainbell.placesearch.domain.placesearch.placelist.placeinfo.PlaceInfoVO;
import com.chainbell.placesearch.domain.placesearch.placelist.placeinfo.PlaceServiceCodeVO;
import com.chainbell.placesearch.domain.placesearch.placelist.placeinfo.kakao.KakaoPlaceInfoVO;
import com.chainbell.placesearch.domain.placesearch.placelist.placeinfo.naver.NaverPlaceInfoVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.json.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class PlaceListVO {

    final int serviceCode = 1;

    List<KakaoPlaceInfoVO> kakaoPlaceInfoVOList = new ArrayList<KakaoPlaceInfoVO>();

    List<NaverPlaceInfoVO> naverPlaceInfoVOList = new ArrayList<NaverPlaceInfoVO>();

    public void setKakaoPlaceInfoVOList(String kakaoPlaceJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            JSONObject kakaoResult = new JSONObject(kakaoPlaceJson);
            for (int i = 0; i < kakaoResult.getJSONArray("documents").length(); i++) {
                if (i == 5) {
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

    public List<PlaceInfoVO> getPlaceInfoList() {

        Map<String, PlaceInfoVO> placeInfo = new HashMap<String, PlaceInfoVO>();

        // 1. kakao 입력
        for (KakaoPlaceInfoVO kakaoPlaceInfoVO : kakaoPlaceInfoVOList) {
            placeInfo.put(
                    kakaoPlaceInfoVO.getPlaceName(),
                    new PlaceInfoVO(
                            kakaoPlaceInfoVO.getAddressName(),
                            kakaoPlaceInfoVO.getRoadAddressName(),
                            kakaoPlaceInfoVO.getPlaceName(),
                            PlaceServiceCodeVO.kakao
                    )
            );
        }

        // 2. naver 입력
        for (NaverPlaceInfoVO naverPlaceInfoVO : naverPlaceInfoVOList) {
            if (placeInfo.containsKey(naverPlaceInfoVO.getTitle())) {
                placeInfo.get(naverPlaceInfoVO.getTitle()).setServiceCode(PlaceServiceCodeVO.duplicated);
            } else {
                placeInfo.put(
                        naverPlaceInfoVO.getTitle(),
                        new PlaceInfoVO(
                                naverPlaceInfoVO.getAddress(),
                                naverPlaceInfoVO.getRoadAddress(),
                                naverPlaceInfoVO.getTitle(),
                                PlaceServiceCodeVO.naver
                        )
                );
            }
        }

        // 3. 정렬
        List<PlaceInfoVO> placeInfoList = new ArrayList<PlaceInfoVO>();
        {
            int count = 0;
            for (String key : placeInfo.keySet()) {
                if(count == 10)
                    break;
                placeInfoList.add(placeInfo.get(key));
                count ++;
            }
        }
        List<PlaceInfoVO> sortedPlaceInfo = placeInfoList.stream()
                .sorted(
                        Comparator.comparingLong(PlaceInfoVO::getServiceCode)
                                .thenComparing(PlaceInfoVO::getName)
                ).collect(Collectors.toList());

        return sortedPlaceInfo;
    }

}
