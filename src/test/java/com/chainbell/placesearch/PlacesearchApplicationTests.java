package com.chainbell.placesearch;

import com.chainbell.placesearch.domain.placesearch.PlaceSearchVO;
import com.chainbell.placesearch.domain.placesearch.placelist.PlaceListVO;
import com.chainbell.placesearch.domain.placesearch.placelist.kakao.KakaoPlaceInfoVO;
import com.chainbell.placesearch.domain.placesearch.placelist.naver.NaverPlaceInfoVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class PlacesearchApplicationTests {

    @Value("${kakao.openapi.host}")
    String kakaoHost;

    @Value("${kakao.openapi.url}")
    String kakaoUrl;

    @Value("${kakao.openapi.restapi.key}")
    String kakaoKey;

    @Value("${naver.openapi.host}")
    String naverHost;

    @Value("${naver.openapi.url}")
    String naverUrl;

    @Value("${naver.openapi.header.client.id}")
    String naverId;

    @Value("${naver.openapi.header.client.secret}")
    String naverSecret;

    @Test
    void contextLoads() throws JSONException, IOException {

        String keyword = "강동구 고덕동 은행";

        PlaceSearchVO placeSearch = PlaceSearchVO.builder()
                .searchKeyword(keyword)
                .placeList(new PlaceListVO())
                .build();

        ObjectMapper mapper = new ObjectMapper();

//		String result = HttpUtil.getRequest(kakaoHost+kakaoUrl, placeSearch.getKakaoGetQueryString(), kakaoKey, null);
        String result = "{\"documents\":[{\"place_url\":\"http:\\/\\/place.map.kakao.com\\/1108529295\",\"place_name\":\"신한은행 고덕동지점\",\"category_group_name\":\"은행\",\"road_address_name\":\"서울 강동구 고덕로 353\",\"category_name\":\"금융,보험 > 금융서비스 > 은행 > 신한은행\",\"distance\":\"\",\"phone\":\"02-427-5700\",\"category_group_code\":\"BK9\",\"x\":\"127.16416537365868\",\"y\":\"37.55679113094169\",\"address_name\":\"서울 강동구 고덕동 693\",\"id\":\"1108529295\"},{\"place_url\":\"http:\\/\\/place.map.kakao.com\\/215427959\",\"place_name\":\"새마을금고 서울강동 고덕지점\",\"category_group_name\":\"은행\",\"road_address_name\":\"서울 강동구 동남로82길 97\",\"category_name\":\"금융,보험 > 금융서비스 > 은행 > 새마을금고\",\"distance\":\"\",\"phone\":\"\",\"category_group_code\":\"BK9\",\"x\":\"127.162505179977\",\"y\":\"37.560479403124\",\"address_name\":\"서울 강동구 고덕동 256\",\"id\":\"215427959\"},{\"place_url\":\"http:\\/\\/place.map.kakao.com\\/461113636\",\"place_name\":\"IBK기업은행365 서울도시철도 엔지니어링\",\"category_group_name\":\"은행\",\"road_address_name\":\"서울 강동구 아리수로87길 32\",\"category_name\":\"금융,보험 > 금융서비스 > 은행 > ATM\",\"distance\":\"\",\"phone\":\"\",\"category_group_code\":\"BK9\",\"x\":\"127.168725128144\",\"y\":\"37.5662929106424\",\"address_name\":\"서울 강동구 강일동 227-1\",\"id\":\"461113636\"},{\"place_url\":\"http:\\/\\/place.map.kakao.com\\/1692470764\",\"place_name\":\"NH농협은행 365코너 상일동역지점\",\"category_group_name\":\"은행\",\"road_address_name\":\"서울 강동구 고덕로 399\",\"category_name\":\"금융,보험 > 금융서비스 > 은행 > ATM\",\"distance\":\"\",\"phone\":\"\",\"category_group_code\":\"BK9\",\"x\":\"127.1701514842556\",\"y\":\"37.55740794759242\",\"address_name\":\"서울 강동구 고덕동 210-1\",\"id\":\"1692470764\"},{\"place_url\":\"http:\\/\\/place.map.kakao.com\\/214288997\",\"place_name\":\"우리은행 365코너 아남아파트상가\",\"category_group_name\":\"은행\",\"road_address_name\":\"서울 강동구 양재대로 1708\",\"category_name\":\"금융,보험 > 금융서비스 > 은행 > ATM\",\"distance\":\"\",\"phone\":\"\",\"category_group_code\":\"BK9\",\"x\":\"127.145138522112\",\"y\":\"37.5580495171042\",\"address_name\":\"서울 강동구 고덕동 486\",\"id\":\"214288997\"},{\"place_url\":\"http:\\/\\/place.map.kakao.com\\/799321931\",\"place_name\":\"가나안신협365코너 고덕지점\",\"category_group_name\":\"은행\",\"road_address_name\":\"서울 강동구 고덕로 385\",\"category_name\":\"금융,보험 > 금융서비스 > 은행 > ATM\",\"distance\":\"\",\"phone\":\"\",\"category_group_code\":\"BK9\",\"x\":\"127.168427731181\",\"y\":\"37.5573725772744\",\"address_name\":\"서울 강동구 고덕동 694\",\"id\":\"799321931\"},{\"place_url\":\"http:\\/\\/place.map.kakao.com\\/1741580600\",\"place_name\":\"NH농협은행 365코너 광주지구축산농협 고덕지점\",\"category_group_name\":\"은행\",\"road_address_name\":\"서울 강동구 고덕로 353\",\"category_name\":\"금융,보험 > 금융서비스 > 은행 > ATM\",\"distance\":\"\",\"phone\":\"\",\"category_group_code\":\"BK9\",\"x\":\"127.16484400753231\",\"y\":\"37.55762901307573\",\"address_name\":\"서울 강동구 고덕동 693\",\"id\":\"1741580600\"},{\"place_url\":\"http:\\/\\/place.map.kakao.com\\/824317400\",\"place_name\":\"하나은행365 신라교역부속영업장2층\",\"category_group_name\":\"은행\",\"road_address_name\":\"서울 강동구 고덕비즈밸리로6길 30\",\"category_name\":\"금융,보험 > 금융서비스 > 은행 > ATM\",\"distance\":\"\",\"phone\":\"\",\"category_group_code\":\"BK9\",\"x\":\"127.15963344224\",\"y\":\"37.5682769307793\",\"address_name\":\"서울 강동구 고덕동 90-2\",\"id\":\"824317400\"},{\"place_url\":\"http:\\/\\/place.map.kakao.com\\/169466483\",\"place_name\":\"롯데ATM 세븐일레븐 고덕리앤파크(CD)\",\"category_group_name\":\"은행\",\"road_address_name\":\"서울 강동구 상일로 137\",\"category_name\":\"금융,보험 > 금융서비스 > 은행 > ATM\",\"distance\":\"\",\"phone\":\"\",\"category_group_code\":\"BK9\",\"x\":\"127.170737292873\",\"y\":\"37.5591973813174\",\"address_name\":\"서울 강동구 고덕동 197-5\",\"id\":\"169466483\"},{\"place_url\":\"http:\\/\\/place.map.kakao.com\\/982704970\",\"place_name\":\"CU ATM 고덕동남점\",\"category_group_name\":\"은행\",\"road_address_name\":\"서울 강동구 동남로81길 20\",\"category_name\":\"금융,보험 > 금융서비스 > 은행 > ATM\",\"distance\":\"\",\"phone\":\"\",\"category_group_code\":\"BK9\",\"x\":\"127.155578669584\",\"y\":\"37.5605554071887\",\"address_name\":\"서울 강동구 고덕동 501\",\"id\":\"982704970\"},{\"place_url\":\"http:\\/\\/place.map.kakao.com\\/981892712\",\"place_name\":\"CU ATM 강동그라시움점\",\"category_group_name\":\"은행\",\"road_address_name\":\"서울 강동구 고덕로83길 42\",\"category_name\":\"금융,보험 > 금융서비스 > 은행 > ATM\",\"distance\":\"\",\"phone\":\"\",\"category_group_code\":\"BK9\",\"x\":\"127.168753819725\",\"y\":\"37.55894074607\",\"address_name\":\"서울 강동구 고덕동 189\",\"id\":\"981892712\"},{\"place_url\":\"http:\\/\\/place.map.kakao.com\\/981760272\",\"place_name\":\"CU ATM 고덕에이스점\",\"category_group_name\":\"은행\",\"road_address_name\":\"서울 강동구 상일로25길 7\",\"category_name\":\"금융,보험 > 금융서비스 > 은행 > ATM\",\"distance\":\"\",\"phone\":\"\",\"category_group_code\":\"BK9\",\"x\":\"127.170009022152\",\"y\":\"37.5614545258754\",\"address_name\":\"서울 강동구 고덕동 173\",\"id\":\"981760272\"},{\"place_url\":\"http:\\/\\/place.map.kakao.com\\/10872540\",\"place_name\":\"하나은행 고덕역지점\",\"category_group_name\":\"은행\",\"road_address_name\":\"서울 강동구 동남로75길 29\",\"category_name\":\"금융,보험 > 금융서비스 > 은행 > 하나은행\",\"distance\":\"\",\"phone\":\"02-486-1111\",\"category_group_code\":\"BK9\",\"x\":\"127.154528708891\",\"y\":\"37.5541525029288\",\"address_name\":\"서울 강동구 명일동 47-1\",\"id\":\"10872540\"},{\"place_url\":\"http:\\/\\/place.map.kakao.com\\/8202406\",\"place_name\":\"KB국민은행 고덕역지점\",\"category_group_name\":\"은행\",\"road_address_name\":\"서울 강동구 고덕로 254\",\"category_name\":\"금융,보험 > 금융서비스 > 은행 > KB국민은행\",\"distance\":\"\",\"phone\":\"02-426-2330\",\"category_group_code\":\"BK9\",\"x\":\"127.153565682676\",\"y\":\"37.554734001402\",\"address_name\":\"서울 강동구 명일동 46\",\"id\":\"8202406\"},{\"place_url\":\"http:\\/\\/place.map.kakao.com\\/8303326\",\"place_name\":\"NH농협은행 명일동지점\",\"category_group_name\":\"은행\",\"road_address_name\":\"서울 강동구 고덕로 262\",\"category_name\":\"금융,보험 > 금융서비스 > 은행 > NH농협은행\",\"distance\":\"\",\"phone\":\"02-442-5544\",\"category_group_code\":\"BK9\",\"x\":\"127.15462031046224\",\"y\":\"37.55466775326654\",\"address_name\":\"서울 강동구 명일동 46-2\",\"id\":\"8303326\"}],\"meta\":{\"total_count\":157,\"is_end\":false,\"pageable_count\":45,\"same_name\":{\"keyword\":\"은행\",\"region\":[],\"selected_region\":\"서울 강동구 고덕동\"}}}";
        placeSearch.getPlaceList().setKakaoPlaceInfoVOList(result);

        //		String result2 = HttpUtil.getRequest(naverHost+naverUrl, placeSearch.getNaverGetQueryString(), kakaoKey, placeSearch.getNaverHeaderInfoFormat(naverId, naverSecret));
        String result2 = "{\t\"lastBuildDate\":\"Sat, 24 Dec 2022 14:31:00 +0900\",\t\"total\":5,\t\"start\":1,\t\"display\":5,\t\"items\":[\t\t{\t\t\t\"title\":\"신한<b>은행<\\/b> 고덕동지점\",\t\t\t\"link\":\"http:\\/\\/www.shinhan.com\\/\",\t\t\t\"category\":\"금융,보험>은행\",\t\t\t\"description\":\"\",\t\t\t\"telephone\":\"\",\t\t\t\"address\":\"서울특별시 강동구 고덕동 693\",\t\t\t\"roadAddress\":\"서울특별시 강동구 고덕로 353 (고덕동)\",\t\t\t\"mapx\":\"326380\",\t\t\t\"mapy\":\"550878\"\t\t},\t\t{\t\t\t\"title\":\"서울강동새마을금고 고덕지점\",\t\t\t\"link\":\"http:\\/\\/www.s-gangdong.co.kr\\/\",\t\t\t\"category\":\"금융서비스>새마을금고\",\t\t\t\"description\":\"\",\t\t\t\"telephone\":\"\",\t\t\t\"address\":\"서울특별시 강동구 고덕동 256-3\",\t\t\t\"roadAddress\":\"서울특별시 강동구 동남로82길 107\",\t\t\t\"mapx\":\"326242\",\t\t\t\"mapy\":\"551245\"\t\t},\t\t{\t\t\t\"title\":\"우리<b>은행<\\/b>365\",\t\t\t\"link\":\"\",\t\t\t\"category\":\"금융,보험>은행\",\t\t\t\"description\":\"\",\t\t\t\"telephone\":\"\",\t\t\t\"address\":\"서울특별시 강동구 고덕동 317-1\",\t\t\t\"roadAddress\":\"서울특별시 강동구 고덕로 183\",\t\t\t\"mapx\":\"324730\",\t\t\t\"mapy\":\"550765\"\t\t},\t\t{\t\t\t\"title\":\"우리<b>은행<\\/b> 365 아남아파트상가\",\t\t\t\"link\":\"\",\t\t\t\"category\":\"금융,보험>은행\",\t\t\t\"description\":\"\",\t\t\t\"telephone\":\"\",\t\t\t\"address\":\"서울특별시 강동구 고덕동 486 [아남아파트 상가건물내 1층 출입구]\",\t\t\t\"roadAddress\":\"서울특별시 강동구 양재대로 1708 [아남아파트 상가건물내 1층 출입구]\",\t\t\t\"mapx\":\"324669\",\t\t\t\"mapy\":\"551003\"\t\t},\t\t{\t\t\t\"title\":\"새마을금고365\",\t\t\t\"link\":\"\",\t\t\t\"category\":\"금융,보험>은행\",\t\t\t\"description\":\"\",\t\t\t\"telephone\":\"\",\t\t\t\"address\":\"서울특별시 강동구 고덕동 256\",\t\t\t\"roadAddress\":\"서울특별시 강동구 동남로82길 97\",\t\t\t\"mapx\":\"326207\",\t\t\t\"mapy\":\"551252\"\t\t}\t]}";
        placeSearch.getPlaceList().setNaverPlaceInfoVOList(result2);

        System.out.println(placeSearch.getPlaceList().getKakaoPlaceInfoVOList());
        System.out.println(placeSearch.getPlaceList().getNaverPlaceInfoVOList());

    }

}
