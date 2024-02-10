package com.ssafy.tranvel.controller;

import com.ssafy.tranvel.dto.ResponseDto;
import com.ssafy.tranvel.service.FoodGameService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@Setter
@RequiredArgsConstructor
@RestController
@RequestMapping("/foodGame")
public class FoodGameController {

    private ResponseDto response;
    private final FoodGameService foodGameService;


    // 그냥 stomp?
//    // 방장이 음식게임 시작. roomId를 받으면 새로운 foodGame 시작. 해당 게임의 Id(foodGameHistoryId) 반환
//    // stomp(게임알림)도 같이 발신
//    @PostMapping("/start")
//    public ResponseEntity<ResponseDto> startFoodGame(Long roomId){
//        Long foodGameHistoryId = foodGameService.startFoodGame(roomId);
//        response = new ResponseDto(true, "음식 선정 게임 개설", foodGameHistoryId);
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }

}

