package com.ssafy.tranvel.controller;


import com.ssafy.tranvel.dto.*;
import com.ssafy.tranvel.entity.JoinUser;
import com.ssafy.tranvel.entity.RoomHistory;
import com.ssafy.tranvel.entity.User;
import com.ssafy.tranvel.repository.AdjustmentGameHistoryRepository;
import com.ssafy.tranvel.repository.RoomHistoryRepository;
import com.ssafy.tranvel.repository.UserRepository;
import com.ssafy.tranvel.service.AdjustmentGameHistoryService;
import com.ssafy.tranvel.service.RoomHistoryService;
import com.ssafy.tranvel.utility.SecurityUtility;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Getter @Setter
@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private ResponseDto response;
    private final UserRepository userRepository;
    private final RoomHistoryService roomHistoryService;
    private final RoomHistoryRepository roomHistoryRepository;
    private final AdjustmentGameHistoryService adjustmentGameHistoryService;
    private final AdjustmentGameHistoryRepository adjustmentGameHistoryRepository;
    private final SimpMessageSendingOperations sendingOperations;

    @PostMapping("")
    public ResponseEntity<ResponseDto> getRoomHistoryList(@RequestBody @Validated RoomHistoryDto roomHistoryDto) {

        // service 패키지로 이동예정
//        RoomHistoryDto info = roomHistoryDto;
//        info.setUserEmail(SecurityUtility.getCurrentUserId());
//
//        /*
//        List<RoomHistory>
//        roomId, data, images, roomName, balanceResult
//         */
//        List<RoomMainResponseDto> roomResponse = new ArrayList<>();
//
//
//        List<RoomHistory> roomHistoryList = roomHistoryService.getAllRoomHistories(info);
//        for (int idx = 0; idx < roomHistoryList.size(); idx ++) {
//            RoomMainResponseDto roomMainResponseDto = RoomMainResponseDto.builder()
//                    .roomid(roomHistoryList.get(idx).getId())
//                    .roomName(roomHistoryList.get(idx).getRoomName())
//                    .startDate(roomHistoryList.get(idx).getStartDate())
//                    .endDate(roomHistoryList.get(idx).getEndDate())
//                    .balanceResult(roomHistoryList.get(idx).getBalanceResult())
//                    .images(null)
//                    .build();
//            roomResponse.add(roomMainResponseDto);
//        }
        List<RoomMainResponseDto> roomResponse = roomHistoryService.filteredRoomInfo(roomHistoryDto);

        response = new ResponseDto(true, "방 기록 전체 조회", roomResponse);
        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> postRoomHistory(@RequestBody @Validated RoomHistoryDto roomHistoryDto) {

        RoomHistory roomHistory = roomHistoryService.createRoomHistory(roomHistoryDto);

        RoomInsideDto roomInnerResponse = roomHistoryService.filteredRoomInsideInfo(roomHistory);
        response = new ResponseDto(true, "방 생성 완료", roomInnerResponse);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //addJoinUser(Long userId, String roomCode, String inputRoomPassword
    @PostMapping("/enter")
    public ResponseEntity<ResponseDto> enterRoom(@RequestBody @Validated RoomHistoryDto roomHistoryDto) {
        RoomHistoryDto info = roomHistoryDto;
        RoomHistory roomHistory = roomHistoryRepository.findByRoomCode(roomHistoryDto.getRoomCode()).get();
        roomHistoryDto.setUserEmail(SecurityUtility.getCurrentUserId());
        roomHistoryService.addJoinUser(roomHistory);
        List<JoinUser> joinUser = roomHistoryRepository.findByRoomCode(roomHistoryDto.getRoomCode()).get().getJoinUser();

        RoomInsideDto roomInnerResponse = roomHistoryService.filteredRoomInsideInfo(roomHistory);

        response = new ResponseDto(true, "방 게임 입장", roomInnerResponse);
        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/detail")
    public ResponseEntity<ResponseDto> getRoomDetailHistory(@RequestBody @Validated RoomHistoryDto roomHistoryDto) {
        RoomHistory roomHistory = roomHistoryService.getRoomDetailHistory(roomHistoryDto);

        RoomInsideDto roomInnerResponse = roomHistoryService.filteredRoomInsideInfo(roomHistory);
        response = new ResponseDto(true, "방 게임 기록 조회", roomInnerResponse);



        /*

        { result : true, msg : "~~", data: [

        List<RoomHistory>
        roomId, data, images, roomName, foodHistory, AttractionHistory, balanceResult


        date, images, user : { userId, userProfile, profit}
         */

        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/finish")
    public ResponseEntity<ResponseDto> finishRoomHistory(@RequestBody @Validated RoomHistoryDto roomHistoryDto) {
        roomHistoryService.finishRoomHistory(roomHistoryDto);

        response = new ResponseDto(true, "방 게임 기록 종료", roomHistoryRepository.findById(roomHistoryDto.getRoomId()).get());
        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteRoomHistory(@RequestBody @Validated RoomHistoryDto roomHistoryDto) {
        roomHistoryService.deleteRoomHistory(roomHistoryDto);

        response = new ResponseDto(true, "방 게임 기록 삭제", roomHistoryDto.getRoomId());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("{room-id}/adjustmentgame")
    public ResponseEntity<ResponseDto> getRoomUsers(@PathVariable("room-id") @Validated Long roomId) {
        List<JoinUserInfoDto> joinUserInfoDtos = adjustmentGameHistoryService.getJoinUsers(roomId);

        response = new ResponseDto(true, "방의 인원 목록.",joinUserInfoDtos);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("{room-id}/adjustmentgame")
    public ResponseEntity<ResponseDto> createAdjustmentGameHistory(@RequestBody @Validated AdjustmentGameHistoryDto adjustmentGameHistoryDto) {
        int moneyResult = adjustmentGameHistoryService.adjustment(adjustmentGameHistoryDto); // 1인당 정산되는 금액

        response = new ResponseDto(true,"정산 실시, 1/N 액수", moneyResult);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
