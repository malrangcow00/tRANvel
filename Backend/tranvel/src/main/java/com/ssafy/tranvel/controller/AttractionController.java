package com.ssafy.tranvel.controller;

import com.ssafy.tranvel.dto.AttractionGameRecordDto;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;
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

    // 위도, 경도 기준 30km 이내 모든 관광명소
    @GetMapping("/attractions/nearby")
    public ResponseEntity<ResponseDto> getAttractionsIn30Km(@RequestParam double latitude, @RequestParam double longitude) {
        List<AttractionList> attractionsIn30Km = attractionService.getAttractionsIn30Km(latitude, longitude);

        response = new ResponseDto(true, "30km 이내 모든 관광명소", attractionsIn30Km);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 위도, 경도 기준 30km 이내 모든 관광명소 중 랜덤 한 개
    @GetMapping("/attractions/nearbyrandom")
    public ResponseEntity<ResponseDto> getAttractionIn30KmRandomly(@RequestParam double latitude, @RequestParam double longitude) {
        AttractionList attractionIn30KmRandomly = attractionService.getAttractionIn30KmRandomly(latitude, longitude);

        response = new ResponseDto(true, "30km 이내 모든 관광명소 중 랜덤 한 개", attractionIn30KmRandomly);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

//    @PostMapping(value = "/attractions/record", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<ResponseDto> recordAttractionGame(@RequestPart AttractionGameRecordDto attractionGameRecordDto, @RequestPart(value = "image",required = false) MultipartFile image) {
//        attractionService.recordAttractionGame(attractionGameRecordDto, image);
//
//        response = new ResponseDto(true, "관광지 게임 저장 완료", null);
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }

    // deleteAttractionGameHistory의 contentId를 받아 해당 기록 삭제
    @DeleteMapping("/attractions/delete")
    public ResponseEntity<ResponseDto> deleteAttractionGameHistory(Long contentId) {
        attractionService.deleteAttractionGameHistory(contentId);

        response = new ResponseDto(true, "해당 관광지 게임 기록 삭제 완료", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/check")
    public void check() {
        attractionService.saveDataFromJsonFile("src/main/resources/AttractionJson.json");
    }
}
