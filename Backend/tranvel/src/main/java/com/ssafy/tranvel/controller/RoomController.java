package com.ssafy.tranvel.controller;


import com.ssafy.tranvel.dto.*;
import com.ssafy.tranvel.entity.*;
import com.ssafy.tranvel.repository.AdjustmentGameHistoryRepository;
import com.ssafy.tranvel.repository.RoomHistoryRepository;
import com.ssafy.tranvel.repository.UserRepository;
import com.ssafy.tranvel.service.AdjustmentGameHistoryService;
import com.ssafy.tranvel.service.AttractionService;
import com.ssafy.tranvel.service.FoodGameService;
import com.ssafy.tranvel.service.RoomHistoryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private final FoodGameService foodGameService;

    private final AttractionService attractionService;

    @PostMapping(value = "")
    public ResponseEntity<ResponseDto> getRoomHistoryList() {
        List<RoomMainResponseDto> roomResponse = roomHistoryService.filteredRoomInfo();

        response = new ResponseDto(true, "방 기록 전체 조회", roomResponse);
        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> postRoomHistory(String roomPassword) {

        RoomHistory roomHistory = roomHistoryService.createRoomHistory(roomPassword);

        RoomInsideDto roomInnerResponse = roomHistoryService.filteredRoomInsideInfo(roomHistory);
        response = new ResponseDto(true, "방 생성 완료", roomInnerResponse);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //addJoinUser(Long userId, String roomCode, String inputRoomPassword
    @PostMapping("/enter")
    public ResponseEntity<ResponseDto> enterRoom(String roomCode, String roomPassword) {
        RoomHistory roomHistory = roomHistoryRepository.findByRoomCode(roomCode).get();
        roomHistoryService.addJoinUser(roomHistory, roomPassword);
//        List<JoinUser> joinUser = roomHistoryRepository.findByRoomCode(roomHistoryDto.getRoomCode()).get().getJoinUser();

        RoomInsideDto roomInnerResponse = roomHistoryService.filteredRoomInsideInfo(roomHistory);

        response = new ResponseDto(true, "방 게임 입장", roomInnerResponse);
        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/detail")
    public ResponseEntity<ResponseDto> getRoomDetailHistory(Long roomId) {
        RoomHistory roomHistory = roomHistoryService.getRoomDetailHistory(roomId);

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


    @PostMapping("/history")
    public ResponseEntity<ResponseDto> getRoomDetailHistoryTmp(Long roomId) {
        RoomHistory roomHistory = roomHistoryService.getRoomDetailHistory(roomId);

//        Map<String, List<RoomDetailDto>> info = roomHistoryService.roomDetailHistory(roomHistory);
        List<RoomDetailDto> info = roomHistoryService.roomDetailHistory(roomHistory);
        response = new ResponseDto(true, "방 히스토리 상세 기록 조회", info);

        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/finish")
    public ResponseEntity<ResponseDto> finishRoomHistory(Long roomId) {
        roomHistoryService.finishRoomHistory(roomId);

        response = new ResponseDto(true, "방 게임 기록 종료", null);
        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteRoomHistory(Long roomId) {
        roomHistoryService.deleteRoomHistory(roomId);

        response = new ResponseDto(true, "방 게임 기록 삭제", null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 정산을 위해 방의 인원 목록을 불러옴. / param : roomId
    @PostMapping("/adjustment/select")
    public ResponseEntity<ResponseDto> getRoomUsers(Long roomId) {
        List<JoinUserInfoDto> joinUserInfoDtos = adjustmentGameHistoryService.getJoinUsers(roomId);

        response = new ResponseDto(true, "방의 인원 목록.",joinUserInfoDtos);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 입력된 정보에 따라, 선택된 인원들에 대해 price/인원 으로 정산 실시 및 기록, api 명세 참조
    @PostMapping(value = "/adjustment/record", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> createAdjustmentGameHistory(@RequestPart AdjustmentGameHistoryDto adjustmentGameHistoryDto, @RequestPart(value = "image",required = false) MultipartFile image) {
        System.out.println(image == null);
        int moneyResult = adjustmentGameHistoryService.adjustment(adjustmentGameHistoryDto, image); // 1인당 정산되는 금액

        response = new ResponseDto(true,"정산 실시, 1/N 액수", moneyResult);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 방 id에 관련된 모든 정산 기록 정보 / param : roomId
    @PostMapping("/adjustment/getallhistory")
    public ResponseEntity<ResponseDto> getAllAdjustmentHistory(Long roomId) {
        List<AdjustmentGameHistory> adjustmentGameHistoryList = adjustmentGameHistoryService.getAllAdjustmentHistories(roomId);

        response = new ResponseDto(true, "해당 방의 모든 정산 기록", adjustmentGameHistoryList);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // AdjustmentGameHistory 의 한 id에 대한 기록 / param : contentId
    @PostMapping("/adjustment/gethistory")
    public ResponseEntity<ResponseDto> getOneAdjustmentHistory(Long contentId) {
        AdjustmentGameHistory adjustmentGameHistory = adjustmentGameHistoryService.getAdjustmentHistory(contentId);

        response = new ResponseDto(true, "해당 방의 해당 정산 기록", adjustmentGameHistory);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // 방 id에 관련된 모든 FoodGame 기록 정보 / param : roomId
    @PostMapping("/foodgame/getallhistory")
    public ResponseEntity<ResponseDto> getAllFoodGameHistory(Long roomId) {
        List<FoodGameHistory> foodGameHistoryList = foodGameService.getAllFoodGameHistories(roomId);

        response = new ResponseDto(true, "해당 방의 모든 FoodGame 기록", foodGameHistoryList);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // FoodGameHistory 의 한 id에 대한 기록 / param : contentId
    @PostMapping("/foodgame/gethistory")
    public ResponseEntity<ResponseDto> getOneFoodGameHistory(Long contentId) {
        FoodGameHistory foodGameHistory = foodGameService.getFoodGameHistory(contentId);

        response = new ResponseDto(true, "해당 방의 해당 FoodGame 기록", foodGameHistory);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

//    // 방 인원 중 랜덤 한 명 닉네임
//    @PostMapping("/test")
//    public ResponseEntity<Long> test(Long roomId) {
//        System.out.println("RoomController.test");
//        System.out.println(roomId);
//        String attractionGamePlayer = attractionService.getAttractionGamePlayer(roomId);
//
//        response = new ResponseDto(true, "ok", attractionGamePlayer);
//        return ResponseEntity.status(HttpStatus.OK).body(roomId);
//    }
}
