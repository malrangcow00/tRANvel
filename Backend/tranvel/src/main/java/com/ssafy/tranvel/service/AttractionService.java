package com.ssafy.tranvel.service;


import com.ssafy.tranvel.repository.AttractionRepository;
import io.swagger.v3.core.util.Json;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
@Getter @Setter
@RequiredArgsConstructor
public class AttractionService {

    private AttractionRepository attractionRepository;


    public Object loadAttractionList() throws UnsupportedEncodingException {
        String serviceKey = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FmeS5qZW9uZ2h3NG5AZ21haWwuY29tIiwiYXV0aCI6IlVTRVIiLCJleHAiOjE3MDcxOTI1MDh9.I8rHsyEpiKW0huPaFUsi8sym0CKx-4dx_JdQolF8NeTwe8ynUpEVBfKQz0k9y8XXooICidTVVp-8CL-Kgsnyeg";
        String apiUrl = "http://api.data.go.kr/openapi/tn_pubr_public_trrsrt_api";
        String encodedKey = URLEncoder.encode(serviceKey, "UTF-8");

        WebClient webClient = WebClient.create(apiUrl);

        try {
            Object response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("serviceKey", encodedKey)
                            .queryParam("type", "Json")
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            System.out.println(response);
            return response;
            // 여기서 응답을 사용하거나 처리합니다.

        } catch (WebClientResponseException e) {
            // WebClientResponseException에서 상세한 오류 정보를 얻습니다.
            System.err.println("WebClient error: " + e.getRawStatusCode() + " " + e.getStatusText());
            return e;
        }

    }
}
