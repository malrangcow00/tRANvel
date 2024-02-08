package com.ssafy.tranvel.controller;

import com.ssafy.tranvel.dto.ResponseDto;
import com.ssafy.tranvel.entity.AttractionList;
import com.ssafy.tranvel.repository.AttractionRepository;
import com.ssafy.tranvel.service.AttractionService;
import com.ssafy.tranvel.utility.DistanceCalculator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Getter @Setter
@RequiredArgsConstructor
public class AttractionController {

    private ResponseDto response;
    private final AttractionService attractionService;
    private final AttractionRepository attractionRepository;

//    @GetMapping("/attraction")
//    public ResponseEntity<ResponseDto> getAttractionInfos() throws UnsupportedEncodingException {
//        Object attractionList = attractionService.loadAttractionList();
//        response = new ResponseDto(true, "관광지 정보 저장", attractionList);
//
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }


//    @Autowired
//    public AttractionController(AttractionRepository attractionRepository) {
//        this.attractionRepository = attractionRepository;
//    }

    @GetMapping("/attractions/nearby")
    public List<AttractionList> getAttractionsIn30Km(@RequestParam double latitude, @RequestParam double longitude) {
        List<AttractionList> allAttractions = attractionRepository.findAll();

        return allAttractions.stream()
                .filter(attraction ->
                        DistanceCalculator.calculateDistance(
                                latitude,
                                longitude,
                                Double.parseDouble(attraction.getLatitude()),
                                Double.parseDouble(attraction.getLongitude())) <= 30)
                .collect(Collectors.toList());
    }

    @GetMapping("/check")
    public void check() {
        attractionService.saveDataFromJsonFile("src/main/resources/AttractionJson.json");
    }
}
