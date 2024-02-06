package com.ssafy.tranvel.controller;


import com.ssafy.tranvel.dto.ResponseDto;
import com.ssafy.tranvel.dto.RoomHistoryDto;
import com.ssafy.tranvel.dto.RoomMainResponseDto;
import com.ssafy.tranvel.dto.StompDto;
import com.ssafy.tranvel.entity.JoinUser;
import com.ssafy.tranvel.entity.RoomHistory;
import com.ssafy.tranvel.entity.User;
import com.ssafy.tranvel.repository.RoomHistoryRepository;
import com.ssafy.tranvel.repository.UserRepository;
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
    private final SimpMessageSendingOperations sendingOperations;

    @PostMapping("")
    public ResponseEntity<ResponseDto> getRoomHistoryList(@RequestBody @Validated RoomHistoryDto roomHistoryDto) {
        RoomHistoryDto info = roomHistoryDto;
        info.setUserEmail(SecurityUtility.getCurrentUserId());

        /*
        List<RoomHistory>
        roomId, data, images, roomName, balanceResult
         */
        List<RoomMainResponseDto> roomResponse = new ArrayList<>();


        List<RoomHistory> roomHistoryList = roomHistoryService.getAllRoomHistories(info);
        for (int idx = 0; idx < roomHistoryList.size(); idx ++) {
            RoomMainResponseDto roomMainResponseDto = RoomMainResponseDto.builder()
                    .roomid(roomHistoryList.get(idx).getId())
                    .roomName(roomHistoryList.get(idx).getRoomName())
                    .startDate(roomHistoryList.get(idx).getStartDate())
                    .endDate(roomHistoryList.get(idx).getEndDate())
                    .balanceResult(roomHistoryList.get(idx).getBalanceResult())
                    .images(null)
                    .build();
            roomResponse.add(roomMainResponseDto);
        }


        response = new ResponseDto(true, "방 기록 전체 조회", roomResponse);
        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/create")
    public ResponseEntity<ResponseDto> postRoomHistory(@RequestBody @Validated RoomHistoryDto roomHistoryDto) {

        RoomHistory roomHistory = roomHistoryService.createRoomHistory(roomHistoryDto);
        response = new ResponseDto(true, "방 생성 완료", roomHistory);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    //addJoinUser(Long userId, String roomCode, String inputRoomPassword
    @PostMapping("/enter")
    public ResponseEntity<ResponseDto> enterRoom(@RequestBody @Validated RoomHistoryDto roomHistoryDto) {
        RoomHistoryDto info = roomHistoryDto;
        roomHistoryDto.setUserEmail(SecurityUtility.getCurrentUserId());
        roomHistoryService.addJoinUser(info);
        List<JoinUser> joinUser = roomHistoryRepository.findByRoomCode(roomHistoryDto.getRoomCode()).get().getJoinUser();

        response = new ResponseDto(true, "방 게임 입장", joinUser);

//        // Stomp 메세지 작성/발신
//        String sender = userRepository.findById(roomHistoryDto.getUserId()).get().getNickName();
//        String message = sender+"님이 입장하였습니다.";
//        sendingOperations.convertAndSend("/topic/tranvel/room/" + roomHistoryDto.getRoomId(), message);

        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/detail")
    public ResponseEntity<ResponseDto> getRoomDetailHistory(@RequestBody @Validated RoomHistoryDto roomHistoryDto) {
        RoomHistory roomHistory = roomHistoryService.getRoomDetailHistory(roomHistoryDto);
        response = new ResponseDto(true, "방 게임 기록 조회", roomHistory);



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

//        String message = "여행이 종료되었습니다."; // 멘트 더 꾸미는게 좋나..?
//        sendingOperations.convertAndSend("/topic/chat/room/"+roomHistoryDto.getRoomId(),message);

        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteRoomHistory(@RequestBody @Validated RoomHistoryDto roomHistoryDto) {
        roomHistoryService.deleteRoomHistory(roomHistoryDto);

        response = new ResponseDto(true, "방 게임 기록 삭제", roomHistoryDto.getRoomId());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
