package com.ssafy.tranvel.controller;

import com.ssafy.tranvel.dto.StompDto;
import com.ssafy.tranvel.repository.RoomHistoryRepository;
import com.ssafy.tranvel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StompController {

    private final UserRepository userRepository;
    private final RoomHistoryRepository roomHistoryRepository;
    private final SimpMessageSendingOperations sendingOperations;


    // 방장의 (푸쉬 알람)공지 -> 계획에 없었으니 빼도 됨
    @MessageMapping("/tranvel/notice")
    public void notice(StompDto message) {
        if (StompDto.MessageType.NOTICE.equals(message.getType())) {
            sendingOperations.convertAndSend("/topic/tranvel/room/"+message.getRoomId(),message); // 문자열로 변환해서 발신
        }

    }


    // 유저가 방에 입장 시, 방에 입장해 있던 인원 모두에게 그 사실을 알림
    @MessageMapping("/tranvel/enter") // 클라이언트가 이 url로 메세지를 보내면 다음을 실행함
    public void roomEnter(StompDto message) {
        if (StompDto.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(userRepository.findById(message.getSender_id()).get().getNickName()+"님이 입장하였습니다.");
            sendingOperations.convertAndSend("/topic/tranvel/room/"+message.getRoomId(),message); // 문자열로 변환해서 발신
        }
    }

    // 방장이 여행 종료 시, 모두에게 그 사실을 알림
    @MessageMapping("/tranvel/close")
    public void roomClose(StompDto message) {
        message.setMessage("여행이 종료되었습니다.");
        sendingOperations.convertAndSend("/topic/tranvel/room/"+message.getRoomId(),message);
    }

    ////////////////////////////////////////////////////////////////////////////////

    @MessageMapping("/tranvel/foodgame/start")
    public void foodGameStart(StompDto message) {
        message.setMessage("음식 선택 게임을 시작합니다.");
        sendingOperations.convertAndSend("/topic/tranvel/room/"+message.getRoomId(),message);
    }

    @MessageMapping("/tranvel/foodgame/end")
    public void foodGameEnd(StompDto message) {
        message.setMessage("음식 선택 게임이 완료되었습니다.");
        sendingOperations.convertAndSend("/topic/tranvel/room/"+message.getRoomId(),message);
    }

    ////////////////////////////////////////////////////////////////////////////////

    @MessageMapping("/tranvel/attractiongame/start")
    public void attractionGameStart(StompDto message) {
        message.setMessage("행선지 선택 게임을 시작합니다.");
        sendingOperations.convertAndSend("/topic/tranvel/room/"+message.getRoomId(),message);
    }

    @MessageMapping("/tranvel/attractiongame/end")
    public void attractionGameEnd(StompDto message) {
        message.setMessage("행선지 선택 게임이 완료되었습니다.");
        sendingOperations.convertAndSend("/topic/tranvel/room/"+message.getRoomId(),message);
    }

    ////////////////////////////////////////////////////////////////////////////////

    @MessageMapping("/tranvel/adjustmentgame/start")
    public void adjustmentattractiongameStart(StompDto message) {
        message.getRoomId();
        message.setMessage("정산 게임을 시작합니다.");
        sendingOperations.convertAndSend("/topic/tranvel/room/"+message.getRoomId(),message);
    }

    @MessageMapping("/tranvel/adjustmentgame/end")
    public void adjustmentattractiongameEnd(StompDto message) {
        message.setMessage("정산 게임이 완료되었습니다.");
        sendingOperations.convertAndSend("/topic/tranvel/room/"+message.getRoomId(),message);
    }

