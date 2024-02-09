package com.ssafy.tranvel.controller;

import com.ssafy.tranvel.dto.JoinUserInfoDto;
import com.ssafy.tranvel.dto.StompDto;
import com.ssafy.tranvel.entity.JoinUser;
import com.ssafy.tranvel.entity.RoomHistory;
import com.ssafy.tranvel.entity.User;
import com.ssafy.tranvel.repository.RoomHistoryRepository;
import com.ssafy.tranvel.repository.UserRepository;
import com.ssafy.tranvel.service.AttractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequiredArgsConstructor
public class StompController {

    private final UserRepository userRepository;
    private final AttractionService attractionService;
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
        sendingOperations.convertAndSend("/topic/tranvel/rooms/" + message.getRoomId(), message);
    }

    // 새로고침 요청
    @MessageMapping("/tranvel/callRefresh")
    public void callRefresh(StompDto message) {
        message.setMessage("정보가 변경되었습니다. 새로고침 해주세요");
        sendingOperations.convertAndSend("/topic/tranvel/callRefresh/" + message.getRoomId(), message);
    }

    @MessageMapping("/tranvel/foodgame")
    public void foodGame(StompDto message) {
        if (StompDto.MessageType.ENTER.equals(message.getType())) {
            message.setMessage("음식 선택 게임을 시작합니다.");
        } else if (StompDto.MessageType.CLOSE.equals(message.getType())) {
            message.setMessage("음식 선택 게임이 완료되었습니다.");
        }
        sendingOperations.convertAndSend("/topic/tranvel/foodgame/" + message.getRoomId(), message);
    }

    @MessageMapping("/tranvel/attractiongame")
    public void attractionGame(StompDto message) {
        if (StompDto.MessageType.ENTER.equals(message.getType())) {
            message.setMessage("행선지 선택 게임을 시작합니다.");
        } else if (StompDto.MessageType.CLOSE.equals(message.getType())) {
            message.setMessage("행선지 선택 게임이 완료되었습니다.");
        }
        sendingOperations.convertAndSend("/topic/tranvel/attractiongame/" + message.getRoomId(), message);
    }

    @MessageMapping("/tranvel/adjustmentgame")
    public void adjustmentGame(StompDto message) {
        if (StompDto.MessageType.ENTER.equals(message.getType())) {
            message.setMessage("정산 게임을 시작합니다.");
        } else if (StompDto.MessageType.CLOSE.equals(message.getType())) {
            message.setMessage("정산 게임이 완료되었습니다.");
        }
        sendingOperations.convertAndSend("/topic/tranvel/adjustmentgame/" + message.getRoomId(), message);
    }

    // roomId 필요. 방 인원 중 한명을 뽑아 닉네임을 보냄.
    //
    @MessageMapping("/tranvel/getplayer")
    public void getAttractionGamePlayer(StompDto message) {
        String attractionGamePlayerNickname = attractionService.getAttractionGamePlayer(Long.parseLong(message.getRoomId()));
        if (attractionGamePlayerNickname == null) {
            attractionGamePlayerNickname = "이름없음 ";
        }

        message.setMessage("여행지를 뽑을 사람은 " + attractionGamePlayerNickname + "님 입니다.");
        System.out.println("Stomp Send : attractionGamePlayerNickname = " + attractionGamePlayerNickname);

        sendingOperations.convertAndSend("/topic/tranvel/getplayer/" + message.getRoomId(), message);
    }

    @MessageMapping("/tranvel/foodgameready")
    public void foodGameReady(StompDto message) {
        // 닉네임, 메뉴 접수
        message.getSender_id();
        message.getMessage();
        // 참여자 닉네임 리스트, 불참자 닉네임 리스트, 메뉴 리스트 발송

    }
}