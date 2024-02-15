package com.ssafy.tranvel.service;

import com.ssafy.tranvel.dto.RoomHistoryDto;
import com.ssafy.tranvel.dto.RoomInsideResponseDto;
import com.ssafy.tranvel.dto.RoomMainResponseDto;
import com.ssafy.tranvel.dto.RoomDetailResponseDto;
import com.ssafy.tranvel.entity.*;
import com.ssafy.tranvel.repository.JoinUserRepository;
import com.ssafy.tranvel.repository.RoomHistoryRepository;
import com.ssafy.tranvel.repository.UserRepository;
import com.ssafy.tranvel.utility.SecurityUtility;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@Getter @Setter
@RequiredArgsConstructor
public class RoomHistoryService {

    private final RoomHistoryRepository roomHistoryRepository;
    private  final UserRepository userRepository;
    private  final JoinUserRepository joinUserRepository;

    public List<RoomHistory> getAllRoomHistories(RoomHistoryDto roomHistoryDto) {
//        User user = userRepository.findById(roomHistoryDto.getUserId()).get();
//        JoinUser joinUser = joinUserRepository.findByUserId(roomHistoryDto.getUserId()).get();

//        return roomHistoryRepository.findByJoinUser(user.getJoinUser());
        Long userId = userRepository.findByEmail(SecurityUtility.getCurrentUserId()).get().getId();
        List<JoinUser> userList = joinUserRepository.findAllByUserId(userId).get();
        List<RoomHistory> roomHistoryList = new ArrayList<>();
        for (int idx = 0; idx < userList.size(); idx ++){
            roomHistoryList.add(userList.get(idx).getRoomHistory());
        }
//        return user.getRoomHistories();
        return roomHistoryList;
    }


    public List<RoomMainResponseDto> filteredRoomInfo() {
        RoomHistoryDto info = new RoomHistoryDto();
//        info.setUserEmail(SecurityUtility.getCurrentUserId());

        /*
        List<RoomHistory>
        roomId, data, images, roomName, balanceResult
         */
        List<RoomMainResponseDto> roomResponse = new ArrayList<>();
        System.out.println("a");


        List<RoomHistory> roomHistoryList = getAllRoomHistories(info);
        for (int idx = 0; idx < roomHistoryList.size(); idx ++) {
            List<String> imageList = new ArrayList<>();
            List<AdjustmentGameHistory> adjustmentGameHistories = roomHistoryList.get(idx).getAdjustmentGameHistories();
            String imageRoute;
            if (!adjustmentGameHistories.isEmpty()) {
                System.out.println("b");
                for (int adjustmentGameHistory = 0; adjustmentGameHistory < adjustmentGameHistories.size(); adjustmentGameHistory ++) {
                    if (!adjustmentGameHistories.get(adjustmentGameHistory).getImages().isEmpty()) {
                        List<AdjustmentImage> adjustmentImages = adjustmentGameHistories.get(adjustmentGameHistory).getImages();
                        if (!adjustmentImages.isEmpty()) {
                            for (int adjustmentImage = 0; adjustmentImage < adjustmentImages.size(); adjustmentImage ++) {
                                imageRoute = "/" + roomHistoryList.get(idx).getId() + "/adjustment/" + adjustmentImages.get(adjustmentImage).getId().toString() + ".jpg";
                                imageList.add(imageRoute);
                            }
                        }
                        System.out.println("c");
                    }
                    System.out.println(adjustmentGameHistories.get(adjustmentGameHistory).getId());
                }
            }
            List<AttractionGameHistory> attractionGameHistories = roomHistoryList.get(idx).getAttractionGameHistories();
            if (!attractionGameHistories.isEmpty()) {
                System.out.println("d");
                for (int attractionGaemHistory = 0; attractionGaemHistory < attractionGameHistories.size(); attractionGaemHistory ++) {
                    if (!attractionGameHistories.get(attractionGaemHistory).getImages().isEmpty()) {
                        List<AttractionImage> attractionImages = attractionGameHistories.get(attractionGaemHistory).getImages();
                        if (!attractionImages.isEmpty()) {
                            for (int attractionImage = 0; attractionImage < attractionImages.size(); attractionImage ++) {
                                imageRoute = "/" + roomHistoryList.get(idx).getId() + "/attraction/" + attractionImages.get(attractionImage).getId().toString() + ".jpg";
                                imageList.add(imageRoute);
                            }
                        }
//                        imageList.add(attractionGameHistories.get(attractionGaemHistory).getImages());
                        System.out.println("e");
                    }
                }
            }

            List<FoodGameHistory> foodGameHistories = roomHistoryList.get(idx).getFoodGameHistories();
            if (!foodGameHistories.isEmpty()) {
                System.out.println("f");
                for (int foodGameHistory = 0; foodGameHistory < foodGameHistories.size(); foodGameHistory ++) {
                    if (!foodGameHistories.get(foodGameHistory).getImages().isEmpty()) {
                        List<FoodImage> foodImages = foodGameHistories.get(foodGameHistory).getImages();
                        if (!foodImages.isEmpty()) {
                            for (int foodImage = 0; foodImage < foodImages.size(); foodImage ++) {
                                imageRoute = "/" + roomHistoryList.get(idx).getId() + "/food/" + foodImages.get(foodImage).getId().toString() + ".jpg";
                                imageList.add(imageRoute);
                            }
                        }
                        System.out.println("g");
//                        imageList.add(foodGameHistories.get(foodGameHistory).getImages());
                    }
                }
            }

            RoomMainResponseDto roomMainResponseDto = RoomMainResponseDto.builder()
                    .roomid(roomHistoryList.get(idx).getId())
                    .roomName(roomHistoryList.get(idx).getRoomName())
                    .startDate(roomHistoryList.get(idx).getStartDate())
                    .endDate(roomHistoryList.get(idx).getEndDate())
                    .balanceResult(roomHistoryList.get(idx).getBalanceResult())
                    .build();
            if (!imageList.isEmpty()) {
                System.out.println("h");
                roomMainResponseDto.setImages(imageList);
            }
            System.out.println("i");
            roomResponse.add(roomMainResponseDto);
        }
        Collections.sort(roomResponse, (o1, o2) -> o2.getStartDate().compareTo(o1.getStartDate()));
        return roomResponse;
    }


    public RoomInsideResponseDto filteredRoomInsideInfo(RoomHistory roomHistory) {
        Long userId = userRepository.findByEmail(SecurityUtility.getCurrentUserId()).get().getId();
        List<JoinUser> joinUser = roomHistoryRepository.findByRoomCode(roomHistory.getRoomCode()).get().getJoinUser();

        boolean authority = false;

        for (int idx = 0; idx < joinUser.size(); idx ++) {
            if (joinUser.get(idx).getUserId().equals(userId)) {
                authority = joinUser.get(idx).isAuthority();
                break;
            }
        }

        RoomInsideResponseDto info = RoomInsideResponseDto.builder()
                .roomId(roomHistory.getId())
                .roomCode(roomHistory.getRoomCode())
                .roomPassword(roomHistory.getRoomPassword())
                .authority(authority)
                .build();


        return info;
    }


//    public Map<String, List<RoomDetailDto>> roomDetailHistory(RoomHistory roomHistory) {
    public List<RoomDetailResponseDto> roomDetailHistory(RoomHistory roomHistory) {
//        Map<String, List<RoomDetailDto>> info = new HashMap<>();
        List<RoomDetailResponseDto> info = new ArrayList<>();

        List<AdjustmentGameHistory> adjustmentGameHistories = new ArrayList<>(roomHistory.getAdjustmentGameHistories());
        List<AttractionGameHistory> attractionGameHistories = new ArrayList<>(roomHistory.getAttractionGameHistories());
        List<FoodGameHistory> foodGameHistories = new ArrayList<>(roomHistory.getFoodGameHistories());

        String imageRoute;
        /*
        날짜별로 info 에 content 들을 리스트 형태로 넣을것
        Map info ={
         "2024-02-15" : [["contentId": 1, "dateTime": 20240201...], ["contentId": 2, "dateTime": 20240201...], ["contentId": 3, "dateTime": 20240201...]].
         "2024-02-16" : [["contentId": 4, "dateTime": 20240201...], ["contentId": 5, "dateTime": 20240201...], ["contentId": 6, "dateTime": 20240201...]].
         "2024-02-17" : [["contentId": 7, "dateTime": 20240201...], ["contentId": 8, "dateTime": 20240201...], ["contentId": 9, "dateTime": 20240201...]].
         "2024-02-18" : [["contentId": 10, "dateTime": 20240201...], ["contentId": 11, "dateTime": 20240201...], ["contentId": 12, "dateTime": 20240201...]].
        }

        각 content 리스트들을 순회하며 tmpDto 에 해당 정보를 담고, dateTime 을 format 하여 해당 dateTime 을 키값으로 info 에 저장
         */

        if (!adjustmentGameHistories.isEmpty()) {
            for (AdjustmentGameHistory adjustmentGameHistory : adjustmentGameHistories) {
                List<String> adjustmentImageList = new ArrayList<>();
                if (!adjustmentGameHistory.getImages().isEmpty()) {
                    for (AdjustmentImage image : adjustmentGameHistory.getImages()) {
                        imageRoute = "/" + roomHistory.getId() + "/adjustment/" + image.getId().toString() + ".jpg";
                        adjustmentImageList.add(imageRoute);
                    }
                }
                RoomDetailResponseDto roomDetailResponseDto = RoomDetailResponseDto.builder()
                        .contentId(adjustmentGameHistory.getId())
                        .historyCategory("adjustment")
                        .dateTime(adjustmentGameHistory.getDateTime())
                        .detail(adjustmentGameHistory.getDetail())
                        .images(adjustmentImageList)
                        .moneyResult(adjustmentGameHistory.getMoneyResult())
                        .build();
//                String date = roomDetailDto.getDateTime().substring(0, 10);
//                String date = String.join("",roomDetailDto.getDateTime().substring(0, 10).split("-").toString());
//                if (info.get(date).isEmpty()) {
//                    List<RoomDetailDto> innerList = new ArrayList<>();
//                    innerList.add(roomDetailDto);
//                    info.put(date, innerList);
//                } else {
//                    List<RoomDetailDto> innerList = info.get(date);
//                    innerList.add(roomDetailDto);
//                    info.put(date, innerList);
//                }
                info.add(roomDetailResponseDto);
            }
        }

        if (!attractionGameHistories.isEmpty()) {
            for (AttractionGameHistory attractionGameHistory : attractionGameHistories) {
                List<String> attractionImageList = new ArrayList<>();
                if (!attractionGameHistory.getImages().isEmpty()) {
                    for (AttractionImage image : attractionGameHistory.getImages()) {
                        imageRoute = "/" + roomHistory.getId() + "/attraction/" + image.getId().toString() + ".jpg";
                        attractionImageList.add(imageRoute);
                    }
                }
                RoomDetailResponseDto roomDetailResponseDto = RoomDetailResponseDto.builder()
                        .contentId(attractionGameHistory.getId())
                        .historyCategory("attraction")
                        .dateTime(attractionGameHistory.getDateTime())
                        .detail(attractionGameHistory.getAttractionList().getCity())
                        .images(attractionImageList)
                        .latitude(attractionGameHistory.getAttractionList().getLatitude())
                        .longitude(attractionGameHistory.getAttractionList().getLongitude())
//                        .moneyResult(attractionGameHistory.getMoneyResult())
                        .build();
//                String date = roomDetailDto.getDateTime().substring(0, 10);
//                if (info.get(date).isEmpty()) {
//                    List<RoomDetailDto> innerList = new ArrayList<>();
//                    innerList.add(roomDetailDto);
//                    info.put(date, innerList);
//                } else {
//                    List<RoomDetailDto> innerList = info.get(date);
//                    innerList.add(roomDetailDto);
//                    info.put(date, innerList);
//                }
                info.add(roomDetailResponseDto);
            }
        }
        if (!foodGameHistories.isEmpty()) {
            for (FoodGameHistory foodGameHistory : foodGameHistories) {
                List<String> foodGameImageList = new ArrayList<>();
                if (!foodGameHistory.getImages().isEmpty()) {
                    for (FoodImage image : foodGameHistory.getImages()) {
                        imageRoute = "/" + roomHistory.getId() + "/food/" + image.getId().toString() + ".jpg";
                        foodGameImageList.add(imageRoute);
                    }
                }
                RoomDetailResponseDto roomDetailResponseDto = RoomDetailResponseDto.builder()
                        .contentId(foodGameHistory.getId())
                        .historyCategory("food")
                        .dateTime(foodGameHistory.getDateTime())
                        .detail(foodGameHistory.getFoodName())
                        .images(foodGameImageList)
//                        .moneyResult(foodGameHistory)
                        .build();
//                String date = roomDetailDto.getDateTime().substring(0, 10);
//                if (info.get(date).isEmpty()) {
//                    List<RoomDetailDto> innerList = new ArrayList<>();
//                    innerList.add(roomDetailDto);
//                    info.put(date, innerList);
//                } else {
//                    List<RoomDetailDto> innerList = info.get(date);
//                    innerList.add(roomDetailDto);
//                    info.put(date, innerList);
//                }
                info.add(roomDetailResponseDto);
            }
        }
        if (!info.isEmpty()) {
            Collections.sort(info, (o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
        }

//        List<List<?>> tmp = new ArrayList<>();
//        for ()
        return info;

    }




    public String createRoomCode() {
        Random random = new Random();
        StringBuffer tmpCode = new StringBuffer();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        Set<Character> usedChars = new HashSet<>();
        // 6자리의 랜덤 room 코드 생성
        while (tmpCode.length() < 4) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            tmpCode.append(randomChar);
            usedChars.add(randomChar);
        }
        if (roomHistoryRepository.findByRoomCode(tmpCode.toString()).isPresent()) {
            createRoomCode();
        }
        return tmpCode.toString();
    }


    // Long userId, String roomCode, String inputRoomPassword
    public void addJoinUser(RoomHistory info, String roomPassword) {
        Long userId = userRepository.findByEmail(SecurityUtility.getCurrentUserId()).get().getId();
        System.out.println(userId);
//        RoomHistory roomHistory = roomHistoryRepository.findByRoomCode(info.getRoomCode()).get();
        RoomHistory roomHistory = info;

        if (roomHistory.getRoomPassword().equals(roomPassword)) {
            User user = userRepository.findById(userId).get();
            JoinUser joinUser = JoinUser.builder()
                    .authority(roomHistory.getJoinUser() == null)
                    .userId(userId)
                    .roomHistory(roomHistory)
                    .build();
            if (roomHistory.getJoinUser() == null) {
                System.out.println("1");
                List<JoinUser> nowJoin = new ArrayList<>();
                nowJoin.add(joinUser);
                roomHistory.joinUser(nowJoin);
                joinUserRepository.save(joinUser);
            } else {
                List<JoinUser> nowJoin = roomHistory.getJoinUser() == null? null: roomHistory.getJoinUser();

                boolean isCheck = false;
                System.out.println("2");
                for (int idx = 0; idx < nowJoin.size(); idx ++) {
                    if (
                            Objects.equals(nowJoin.get(idx).getUserId().toString(),userId.toString()))
                    {
                        isCheck = true;
                        System.out.println("3");
                        break;
                    }
                }

                if (!isCheck) {
                    System.out.println("4");
                    nowJoin.add(joinUser);
                    joinUserRepository.save(joinUser);
                    roomHistory.joinUser(nowJoin);
                    System.out.println("5");
                }

            }
        }

        // 반환 형태 고려



    }

    public RoomHistory createRoomHistory(String roomPassword) {

        User user = userRepository.findByEmail(SecurityUtility.getCurrentUserId()).get();
        String roomCode = createRoomCode();
        RoomHistory roomHistory = RoomHistory.builder()
                .user(user)
                .roomCode(roomCode)
                // 암호화
                .roomPassword(roomPassword)
                .startDate(LocalDateTime.now(ZoneId.of("Asia/Seoul")).toString())
                .balanceResult(0)
                .nowPlaying(true)
                .joinUser(null)
                .build();
        roomHistoryRepository.save(roomHistory);
        RoomHistory roomHistory1 = roomHistoryRepository.findByRoomCode(roomCode).get();
        addJoinUser(roomHistory1, roomPassword);

        return roomHistory1;
    }


    public RoomHistory getRoomDetailHistory(Long roomId) {
        RoomHistory roomHistory = roomHistoryRepository.findById(roomId).get();

        return roomHistory;
    }

    // 히스토리 수정은 후순위


    @Transactional
    public void finishRoomHistory(Long roomId, String message) {
        System.out.println("RoomHistoryService.finishRoomHistory");
        RoomHistory roomHistory = roomHistoryRepository.findById(roomId).get();
        roomHistory.setRoomName(message);
        roomHistory.finishRoom();
    }


    public void deleteRoomHistory(Long roomId) {
        roomHistoryRepository.deleteById(roomId);
    }
}
