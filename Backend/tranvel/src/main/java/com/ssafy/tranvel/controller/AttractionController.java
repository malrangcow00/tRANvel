package com.ssafy.tranvel.controller;


import com.ssafy.tranvel.dto.ResponseDto;
import com.ssafy.tranvel.service.AttractionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Getter @Setter
@RequiredArgsConstructor
public class AttractionController {


    private ResponseDto response;
    private final AttractionService attractionService;

    @GetMapping("/attraction")
    public ResponseEntity<ResponseDto> getAttractionInfos() {
        Object attractionList = attractionService.loadAttractionList();
        response = new ResponseDto(true, "관광지 정보 저장", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
