package com.ssafy.tranvel.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.tranvel.dto.*;
import com.ssafy.tranvel.entity.*;
import com.ssafy.tranvel.repository.*;
import com.ssafy.tranvel.utility.DistanceCalculator;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;


@Service
@Getter @Setter
@RequiredArgsConstructor
public class AttractionService {

    private final RoomHistoryRepository roomHistoryRepository;
    private final AttractionRepository attractionRepository;
    private final AttractionGameRepository attractionGameRepository;
    private final AttractionListRepository attractionListRepository;
    private final ImageUploadService imageUploadService;
    private final UserRepository userRepository;

    public Object loadAttractionList() throws UnsupportedEncodingException {
        String serviceKey = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FmeS5qZW9uZ2h3NG5AZ21haWwuY29tIiwiYXV0aCI6IlVTRVIiLCJleHAiOjE3MDcxOTI1MDh9.I8rHsyEpiKW0huPaFUsi8sym0CKx-4dx_JdQolF8NeTwe8ynUpEVBfKQz0k9y8XXooICidTVVp-8CL-Kgsnyeg";
        String apiUrl = "http://api.data.go.kr/openapi/tn_pubr_public_trrsrt_api";
        String encodedKey = URLEncoder.encode(serviceKey, "UTF-8");

        WebClient webClient = WebClient.create(apiUrl);

        try {
            Object response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("serviceKey", encodedKey)
                            .queryParam("type", "Json")
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            System.out.println(response);
            return response;
            // 여기서 응답을 사용하거나 처리합니다.

        } catch (WebClientResponseException e) {
            // WebClientResponseException에서 상세한 오류 정보를 얻습니다.
            System.err.println("WebClient error: " + e.getRawStatusCode() + " " + e.getStatusText());
            return e;
        }

    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Object> readDataFromJsonFile(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), objectMapper.getTypeFactory().constructCollectionType(List.class, Object.class));
    }

    public void saveDataFromJsonFile(String filePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            AttractionBaseDto[] attractionArray = objectMapper.readValue(new File(filePath), AttractionBaseDto[].class);
//            List<AttractionList> attractionList = objectMapper.readValue(new File(filePath), new TypeReference<List<AttractionList>>(){});
//            attractionRepository.saveAll(attractionList);
//            System.out.println("데이터 저장이 완료되었습니다.");
//            List<Object> dataList = readDataFromJsonFile(filePath);
            for (int idx = 0; idx < attractionArray.length; idx ++) {
                AttractionList attractionList = AttractionList.builder()
                        .city(attractionArray[idx].getProvidingAgencyName())
                        .description(attractionArray[idx].getIntroduction())
                        .latitude(attractionArray[idx].getLatitude())
                        .longitude(attractionArray[idx].getLongitude())
                        .name(attractionArray[idx].getAttractionName())
                        .build();
                attractionRepository.save(attractionList);
            }
            System.out.println(attractionArray[0].getProvidingAgencyName());
//            attractionRepository.saveAll(dataList);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    // 'roomId' 받아서, 방의 인원 중 한명을 게임 플레이어로 선정해서 '닉네임 반환'
    @Transactional
    public String getAttractionGamePlayer(Long roomId) {
        System.out.println("AttractionService.getAttractionGamePlayer");
        RoomHistory roomHistory = roomHistoryRepository.findById(roomId).get();
        List<JoinUser> joinUsers = roomHistory.getJoinUser();
        List<Long> joinUserUserIds = new ArrayList<>();

        // 유효한 유저인지 확인
        for (JoinUser joinUser : joinUsers) {
            Optional<User> userOptional = userRepository.findById(joinUser.getUserId());
            if (!userOptional.isPresent()) {
                continue;
            }
            Long userId = userOptional.get().getId();
            joinUserUserIds.add(userId);
        }

        Random random = new Random();
        int randomIndex = random.nextInt(joinUserUserIds.size());
        Long selectedJoinUserId = joinUserUserIds.get(randomIndex);
        User user = userRepository.findById(selectedJoinUserId).get();
        System.out.println("AttractionService - SelecetedUser's Nickname = " + user.getNickName());
        return user.getNickName();
    }

    @Transactional
    public List<AttractionList> getAttractionsIn30Km(double latitude, double longitude) {
        List<AttractionList> allAttractions = attractionRepository.findAll();

        return allAttractions.stream()
                .filter(attraction ->
                        DistanceCalculator.calculateDistance(
                                latitude,
                                longitude,
                                Double.parseDouble(attraction.getLatitude()),
                                Double.parseDouble(attraction.getLongitude())) <= 30)
                .collect(Collectors.toList());
    }

    @Transactional
    public AttractionList getAttractionIn30KmRandomly(double latitude, double longitude) {
        List<AttractionList> attractionsIn30Km = getAttractionsIn30Km(latitude, longitude);

        if (attractionsIn30Km.isEmpty()) {
            System.out.println("주어진 위/경도의 30km 이내 관광지 부재");
            return null; // 30km 이내에 관광지가 없을 경우
        }

        Random random = new Random();
        return attractionsIn30Km.get(random.nextInt(attractionsIn30Km.size()));
    }

//    // 여행지게임 기록
//    public void saveAttractionGame(AttractionGameRecordDto attractionGameRecordDto, MultipartFile image) {
//        RoomHistory roomHistory = roomHistoryRepository.findById(attractionGameRecordDto.getRoomId()).get();
//        AttractionList attractionList = attractionListRepository.findById(attractionGameRecordDto.getAttractionListId()).get();
//
//        AttractionGameHistory attractionGameHistory = AttractionGameHistory.builder()
//                .roomHistory(roomHistory)
//        //        .images()
//                .attractionList(attractionList) // 관광지 정보
//                .dateTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")).toString())
//                .build();
//
//        Long contentId = attractionGameRepository.save(attractionGameHistory).getId();
//
//        // 이미지 저장?
//        ImagePostDto info = new ImagePostDto();
//        info.setRoomId(attractionGameRecordDto.getRoomId());
//        info.setContentId(contentId);
//        try {
//            if (image != null) {
//                imageUploadService.uploadImage(info, image, "attraction");
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println("AttractionGameHistory saved");
//    }

    // 여행지게임 기록
    public void saveAttractionGame(Long roomId, Long attractionListId) {
        RoomHistory roomHistory = roomHistoryRepository.findById(roomId).get();
        AttractionList attractionList = attractionListRepository.findById(attractionListId).get();

        AttractionGameHistory attractionGameHistory = AttractionGameHistory.builder()
                .roomHistory(roomHistory)
        //        .images()
                .attractionList(attractionList) // 관광지 정보
                .dateTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")).toString())
                .build();

        attractionGameRepository.save(attractionGameHistory).getId();

        System.out.println("AttractionGameHistory saved");
    }

    // 모든 관광지게임 기록 열람
    public List<AttractionResponseDto> getAllAttractionGameHistories(Long roomId) {
        RoomHistory roomHistory = roomHistoryRepository.findById(roomId).get();
        List<AttractionGameHistory> attractionGameHistoryList = roomHistory.getAttractionGameHistories();
        List<AttractionResponseDto> info = new ArrayList<>();

        String imageRoute;

        for (AttractionGameHistory attractionGameHistory : attractionGameHistoryList) {
            List<String> attractionImageList = new ArrayList<>();
            if (!attractionGameHistory.getImages().isEmpty()) {
                for (AttractionImage image : attractionGameHistory.getImages()) {
                    imageRoute = "/" + roomHistory.getId() + "/attraction/" + image.getId().toString() + ".jpg";
                    attractionImageList.add(imageRoute);
                }
            }
            AttractionResponseDto attractionResponseDto = AttractionResponseDto.builder()
                    .id(attractionGameHistory.getId())
                    .dateTime(attractionGameHistory.getDateTime())
                    .nickName(attractionGameHistory.getNickName())
                    .attractionList(attractionGameHistory.getAttractionList())
                    .images(attractionImageList)
                    .build();
            info.add(attractionResponseDto);
        }


        return info;
    }

    // 한 관광지게임 기록 열람
    public AttractionResponseDto getAttractionGameHistory(Long contentId) {
        AttractionGameHistory attractionGameHistory = attractionGameRepository.findById(contentId).get();
        RoomHistory roomHistory = roomHistoryRepository.findById(attractionGameHistory.getRoomHistory().getId()).get();

//        AttractionResponseDto info =

        String imageRoute;

        List<String> attractionImageList = new ArrayList<>();
        if (!attractionGameHistory.getImages().isEmpty()) {
            for (AttractionImage image : attractionGameHistory.getImages()) {
                imageRoute = "/" + roomHistory.getId() + "/attraction/" + image.getId().toString() + ".jpg";
                attractionImageList.add(imageRoute);
            }
        }

        AttractionResponseDto info = AttractionResponseDto.builder()
                .id(attractionGameHistory.getId())
                .dateTime(attractionGameHistory.getDateTime())
                .nickName(attractionGameHistory.getNickName())
                .attractionList(attractionGameHistory.getAttractionList())
                .images(attractionImageList)
                .build();

        return info;
    }

    public void deleteAttractionGameHistory(Long contentId) {
        AttractionGameHistory attractionGameHistory = attractionGameRepository.findById(contentId).get();
        attractionGameRepository.delete(attractionGameHistory);
        System.out.println("해당 contentId" + contentId + "의 attractionGameHistory 삭제 완료");
    }
}
