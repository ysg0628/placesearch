package com.chainbell.placesearch.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HttpUtil {

    public static String getKakaoRequest(String targetUrl, String query, String authorization) {

        String response = "";

        try {
            if (query != null && query.length() > 0) {
                targetUrl = targetUrl + "?" + query;
            }
            URL url = new URL(targetUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET"); // 전송 방식
            conn.setRequestProperty("Content-Type", "application/json;");

            if (authorization != null && authorization.length() > 0) {
                conn.setRequestProperty("Authorization", authorization);
            }

            conn.setConnectTimeout(5000); // 연결 타임아웃 설정(5초)
            conn.setReadTimeout(5000); // 읽기 타임아웃 설정(5초)
            conn.setDoOutput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));

            String inputLine;
            StringBuffer sb = new StringBuffer();
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
