package com.ssafy.tranvel.controller;


import com.ssafy.tranvel.dto.ResponseDto;
import com.ssafy.tranvel.dto.RoomHistoryDto;
import com.ssafy.tranvel.entity.RoomHistory;
import com.ssafy.tranvel.service.RoomHistoryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Getter @Setter
@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private ResponseDto response;
    private final RoomHistoryService roomHistoryService;

    @PostMapping("/")
    public ResponseEntity<ResponseDto> getRoomHistoryList(RoomHistoryDto roomHistoryDto) {
        List<RoomHistory> roomHistoryList = roomHistoryService.getAllRoomhistories(roomHistoryDto);
        response = new ResponseDto(true, "방 기록 전체 조회", roomHistoryList);
        return  ResponseEntity.status(HttpStatus.OK).body(response);

    }


    @PostMapping("/create")
    public ResponseEntity<ResponseDto> postRoomHistory(RoomHistoryDto roomHistoryDto) {
        System.out.println(roomHistoryDto.getUserId());
        System.out.println(roomHistoryDto.getRoomPassword());

        RoomHistory roomHistory = roomHistoryService.createRoomHistory(roomHistoryDto);
        response = new ResponseDto(true, "방 생성 완료", roomHistory);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
