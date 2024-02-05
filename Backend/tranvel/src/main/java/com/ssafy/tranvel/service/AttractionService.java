package com.ssafy.tranvel.service;


import com.ssafy.tranvel.repository.AttractionRepository;
import io.swagger.v3.core.util.Json;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Getter @Setter
@RequiredArgsConstructor
public class AttractionService {

    private AttractionRepository attractionRepository;


    public Object loadAttractionList() {

        String apiUrl = "http://api.data.go.kr/openapi/tn_pubr_public_trrsrt_api";
        String serviceKey = "8jh%2FjtHrw38hElkRWF0Rm3YUeYr3J0f%2FuRSu11NISoOxhw1S1GkRPm3ztsbR%2FiUEjG8PCf%2BK1%2BOvDLZAbuhRgw%3D%3D";

        WebClient webClient = WebClient.create(apiUrl);

        Object response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("serviceKey", serviceKey)
                        .queryParam("type", "Json")
                        .build())
                .retrieve()
                .bodyToMono(Object.class)
                .block();


        return response;
    }
}
