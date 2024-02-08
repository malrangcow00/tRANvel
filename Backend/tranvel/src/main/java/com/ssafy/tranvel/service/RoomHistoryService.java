package com.ssafy.tranvel.service;

import com.ssafy.tranvel.dto.RoomHistoryDto;
import com.ssafy.tranvel.dto.RoomInsideDto;
import com.ssafy.tranvel.dto.RoomMainResponseDto;
import com.ssafy.tranvel.entity.*;
import com.ssafy.tranvel.repository.JoinUserRepository;
import com.ssafy.tranvel.repository.RoomHistoryRepository;
import com.ssafy.tranvel.repository.UserRepository;
import com.ssafy.tranvel.utility.SecurityUtility;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

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
        // service 패키지로 이동예정
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
        return roomResponse;
    }


    public RoomInsideDto filteredRoomInsideInfo(RoomHistory roomHistory) {
        Long userId = userRepository.findByEmail(SecurityUtility.getCurrentUserId()).get().getId();
        List<JoinUser> joinUser = roomHistoryRepository.findByRoomCode(roomHistory.getRoomCode()).get().getJoinUser();

        boolean authority = false;

        for (int idx = 0; idx < joinUser.size(); idx ++) {
            if (joinUser.get(idx).getUserId().equals(userId)) {
                authority = joinUser.get(idx).isAuthority();
                break;
            }
        }

        RoomInsideDto info = RoomInsideDto.builder()
                .roomCode(roomHistory.getRoomCode())
                .roomPassword(roomHistory.getRoomPassword())
                .authority(authority)
                .build();


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



    public void finishRoomHistory(Long roomId) {
        roomHistoryRepository.findById(roomId).get().finishRoom();
    }


    public void deleteRoomHistory(Long roomId) {
        roomHistoryRepository.deleteById(roomId);
    }
}
