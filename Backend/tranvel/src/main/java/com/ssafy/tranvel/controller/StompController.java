package com.ssafy.tranvel.controller;

import com.ssafy.tranvel.dto.StompDto;
import com.ssafy.tranvel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StompController {

    private final UserRepository userRepository;
    private final SimpMessageSendingOperations sendingOperations;

    // ENTER : 유저 방에 입장 시, CLOSE : 방장이 여행 종료 시, NOTICE : 방장이 공지사항 띄울 때(메세지내용필요)
    // room, Enter만이 모든 유저 적용 유저가 방에 입장 시, 방에 입장해 있던 인원 모두에게 그 사실을 알림
    @MessageMapping("/tranvel/rooms") // 클라이언트가 이 url로 메세지를 보내면 다음을 실행함
    public void room(StompDto message) {
        if (StompDto.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(userRepository.findById(Long.parseLong(message.getSender_id())).get().getNickName() + "님이 입장하였습니다.");
        } else if (StompDto.MessageType.CLOSE.equals(message.getType())) {
            message.setMessage("여행이 종료되었습니다.");
        }
        sendingOperations.convertAndSend("/topic/tranvel/room/" + message.getRoomId(), message);
    }

    @MessageMapping("/tranvel/foodgame")
    public void foodGame(StompDto message) {
        if (StompDto.MessageType.ENTER.equals(message.getType())) {
            message.setMessage("음식 선택 게임을 시작합니다.");
        } else if (StompDto.MessageType.CLOSE.equals(message.getType())) {
            message.setMessage("음식 선택 게임이 완료되었습니다.");
        }
        sendingOperations.convertAndSend("/topic/tranvel/room/" + message.getRoomId(), message);
    }

    @MessageMapping("/tranvel/attractiongame")
    public void attractionGame(StompDto message) {
        if (StompDto.MessageType.ENTER.equals(message.getType())) {
            message.setMessage("행선지 선택 게임을 시작합니다.");
        } else if (StompDto.MessageType.CLOSE.equals(message.getType())) {
            message.setMessage("행선지 선택 게임이 완료되었습니다.");
        }
        sendingOperations.convertAndSend("/topic/tranvel/room/" + message.getRoomId(), message);
    }

    @MessageMapping("/tranvel/adjustmentgame")
    public void adjustmentGame(StompDto message) {
        if (StompDto.MessageType.ENTER.equals(message.getType())) {
            message.setMessage("정산 게임을 시작합니다.");
        } else if (StompDto.MessageType.CLOSE.equals(message.getType())) {
            message.setMessage("정산 게임이 완료되었습니다.");
        }
        sendingOperations.convertAndSend("/topic/tranvel/room/" + message.getRoomId(), message);
    }
}

