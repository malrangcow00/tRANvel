package com.ssafy.tranvel.controller;

import com.ssafy.tranvel.dto.*;
import com.ssafy.tranvel.entity.AttractionList;
import com.ssafy.tranvel.repository.UserRepository;
import com.ssafy.tranvel.service.AttractionService;
import com.ssafy.tranvel.service.FoodGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequiredArgsConstructor
public class StompController {

    private final UserRepository userRepository;
    private final AttractionService attractionService;
    private final FoodGameService foodGameService;
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
            foodGameService.startFoodGame(Long.parseLong(message.getRoomId()));
            message.setMessage("음식 선택 게임을 시작합니다.");
        } else if (StompDto.MessageType.CLOSE.equals(message.getType())) { // 방장이 message에 선정된 음식 메뉴 이름을 돌림
            foodGameService.foodSelected(message);
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

    // 위도, 경도 정보를 보내면 30km 이내 랜덤한 위치를 방의 모든 인원들에게 발송
    // 관광지 뽑기 결과 저장
    @MessageMapping("/tranvel/attractionrandom")
    public void getAttractionIn30KmRandomly(StompAttractionDto message) {
        AttractionList attractionsIn30Km = attractionService.getAttractionIn30KmRandomly(Double.parseDouble(message.getLatitude()), Double.parseDouble(message.getLongitude()));

        StompAttractionResponseDto stompAttractionResponseDto = StompAttractionResponseDto.builder()
                .sender_id(message.getSender_id())
                .roomId(message.getRoomId())
                .name(attractionsIn30Km.getName())
                .latitude(attractionsIn30Km.getLatitude())
                .longitude(attractionsIn30Km.getLongitude())
                .description(attractionsIn30Km.getDescription())
                .city(attractionsIn30Km.getCity())
                .image(attractionsIn30Km.getImage())
                .build();

        // 결과 저장
        attractionService.saveAttractionGame(Long.parseLong(message.getRoomId()), attractionsIn30Km.getId());

        sendingOperations.convertAndSend("/topic/tranvel/attractionrandom/" + message.getRoomId(), stompAttractionResponseDto);
    }

//  방장이 뽑기 버튼을 눌러 룰렛 돌리기 위한 난수를 발생시켜 브로드캐스트
    @MessageMapping("/tranvel/foodgamestart")
    public void foodGamestart(StompDto message) {
        Random random = new Random();
        Float randFloat = 5000f + random.nextFloat() * (5000f);
        Long randLong = 4000L + (long) (random.nextFloat() * 1000L);
        StompFoodGameStartDto stompFoodGameStartDto = StompFoodGameStartDto.builder()
                .roomId(message.getRoomId())
                .randFloat(randFloat)
                .randLong(randLong)
                .build();
        sendingOperations.convertAndSend("/topic/tranvel/foodgamestart/" + message.getRoomId(), stompFoodGameStartDto);
    }

    // Message에 음식 후보 받아서 StompFoodGameDto로 반환
    @MessageMapping("/tranvel/foodgameready")
    public void foodGameReady(StompDto message) {
        // 닉네임, 방, 메뉴 접수
        StompFoodGameDto response = foodGameService.receiveFood(message);
        // 참여자 닉네임 리스트, 불참자 프로필 이미지값 리스트, 지금까지 제출된 음식메뉴 리스트 발송
        sendingOperations.convertAndSend("/topic/tranvel/foodgameready/" + message.getRoomId(), response);
    }

//    // 방장이 StompDto.message에 선정된 음식 담아 쏘면 그 음식을 저장하고 발송함
//    @MessageMapping("/tranvel/selectedfood")
//    public void foodSelected(StompDto message) {
//        foodGameService.foodSelected(message);
//        sendingOperations.convertAndSend("/topic/tranvel/selectedfood/" + message.getRoomId(), message);
//    }
}