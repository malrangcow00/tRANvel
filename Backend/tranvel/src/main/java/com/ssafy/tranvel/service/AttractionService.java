package com.ssafy.tranvel.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.tranvel.dto.AttractionBaseDto;
import com.ssafy.tranvel.entity.AttractionList;
import com.ssafy.tranvel.repository.AttractionRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;


@Service
@Getter @Setter
@RequiredArgsConstructor
public class AttractionService {

    private final AttractionRepository attractionRepository;

//    public Object loadAttractionList() throws UnsupportedEncodingException {
//        String serviceKey = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FmeS5qZW9uZ2h3NG5AZ21haWwuY29tIiwiYXV0aCI6IlVTRVIiLCJleHAiOjE3MDcxOTI1MDh9.I8rHsyEpiKW0huPaFUsi8sym0CKx-4dx_JdQolF8NeTwe8ynUpEVBfKQz0k9y8XXooICidTVVp-8CL-Kgsnyeg";
//        String apiUrl = "http://api.data.go.kr/openapi/tn_pubr_public_trrsrt_api";
//        String encodedKey = URLEncoder.encode(serviceKey, "UTF-8");
//
//        WebClient webClient = WebClient.create(apiUrl);
//
//        try {
//            Object response = webClient.get()
//                    .uri(uriBuilder -> uriBuilder
//                            .queryParam("serviceKey", encodedKey)
//                            .queryParam("type", "Json")
//                            .build())
//                    .retrieve()
//                    .bodyToMono(String.class)
//                    .block();
//            System.out.println(response);
//            return response;
//            // 여기서 응답을 사용하거나 처리합니다.
//
//        } catch (WebClientResponseException e) {
//            // WebClientResponseException에서 상세한 오류 정보를 얻습니다.
//            System.err.println("WebClient error: " + e.getRawStatusCode() + " " + e.getStatusText());
//            return e;
//        }
//    }

//    public Object loadAttractionList() {
//        String serviceKey = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FmeS5qZW9uZ2h3NG5AZ21haWwuY29tIiwiYXV0aCI6IlVTRVIiLCJleHAiOjE3MDcxOTI1MDh9.I8rHsyEpiKW0huPaFUsi8sym0CKx-4dx_JdQolF8NeTwe8ynUpEVBfKQz0k9y8XXooICidTVVp-8CL-Kgsnyeg";
//        String apiUrl = "http://api.data.go.kr/openapi/tn_pubr_public_trrsrt_api";
//
//        WebClient webClient = WebClient.create(apiUrl);
//
//        return webClient.get()
//                .uri(uriBuilder -> uriBuilder
//                        .queryParam("serviceKey", serviceKey)
//                        .queryParam("type", "Json")
//                        .build())
//                .retrieve()
//                .bodyToMono(String.class)
//                .doOnSuccess(response -> System.out.println("Response: " + response))
//                .doOnError(e -> System.err.println("Error: " + e.getMessage()))
//                .block(); // 비동기 처리를 하려면, block() 대신 다른 방식을 고려
//    }


    private final ObjectMapper objectMapper = new ObjectMapper();

//    public List<Object> readDataFromJsonFile(String filePath) throws IOException {
//        return objectMapper.readValue(new File(filePath), objectMapper.getTypeFactory().constructCollectionType(List.class, Object.class));
//    }

    public void saveDataFromJsonFile(String filePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            AttractionBaseDto[] attractionArray = objectMapper.readValue(new File(filePath), AttractionBaseDto[].class);
//            List<AttractionList> attractionList = objectMapper.readValue(new File(filePath), new TypeReference<List<AttractionList>>(){});
//            attractionRepository.saveAll(attractionList);
//            System.out.println("데이터 저장이 완료되었습니다.");
//            List<Object> dataList = readDataFromJsonFile(filePath);
            for (int idx = 0; idx < attractionArray.length; idx ++) {
                AttractionList attractionList = AttractionList.builder()
                        .city(attractionArray[idx].getProvidingAgencyName())
                        .description(attractionArray[idx].getIntroduction())
                        .latitude(attractionArray[idx].getLatitude())
                        .longitude(attractionArray[idx].getLongitude())
                        .name(attractionArray[idx].getAttractionName())
                        .build();
                attractionRepository.save(attractionList);
            }
            System.out.println(attractionArray[0].getProvidingAgencyName());
//            attractionRepository.saveAll(dataList);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    // filterAttractionList
//    public


}
