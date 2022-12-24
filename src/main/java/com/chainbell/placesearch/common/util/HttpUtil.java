package com.chainbell.placesearch.common.util;

import com.chainbell.placesearch.place.dto.service.http.HttpGetQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpHeaders;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Service
public class HttpUtil {

    public static String getRequest(String targetUrl, String queryParam, String authorization) {

        String response = "";

        try {
            if (queryParam == null || authorization == null) {
                return null;
            }

            targetUrl = targetUrl + "?" + queryParam;

            URL url = new URL(targetUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET"); // 전송 방식
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            if (authorization != null && authorization.length() > 0) {
                conn.setRequestProperty("Authorization", authorization);
            }

            conn.setConnectTimeout(5000); // 연결 타임아웃 설정(5초)
            conn.setReadTimeout(5000); // 읽기 타임아웃 설정(5초)
            conn.setDoOutput(true);

            InputStreamReader in = new InputStreamReader((InputStream) conn.getContent(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(in);

            String inputLine;
            StringBuilder sb = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            br.close();

            response = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

}
