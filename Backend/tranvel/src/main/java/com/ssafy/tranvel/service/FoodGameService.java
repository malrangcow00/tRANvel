package com.ssafy.tranvel.service;

import com.ssafy.tranvel.dto.FoodResponseDto;
import com.ssafy.tranvel.dto.StompDto;
import com.ssafy.tranvel.dto.StompFoodGameDto;
import com.ssafy.tranvel.entity.*;
import com.ssafy.tranvel.repository.FoodGameHistoryRepository;
import com.ssafy.tranvel.repository.JoinUserRepository;
import com.ssafy.tranvel.repository.RoomHistoryRepository;
import com.ssafy.tranvel.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class FoodGameService {

    private final FoodGameHistoryRepository foodGameHistoryRepository;
    private final RoomHistoryRepository roomHistoryRepository;
    private final UserRepository userRepository;
    private final JoinUserRepository joinUserRepository;
    private StompFoodGameDto stompFoodGameDto;

    @Transactional
    public Long startFoodGame(Long roomId) {
        System.out.println("FoodGameService.startFoodGame");
        RoomHistory roomHistory = roomHistoryRepository.findById(roomId).get();
        List<JoinUser> joinUsers = roomHistory.getJoinUser();
        List<Long> joinUserUserIds = new ArrayList<>();

        for (JoinUser joinUser : joinUsers) {
            Optional<User> userOptional = userRepository.findById(joinUser.getUserId());
            if (!userOptional.isPresent()) {
                continue;
            }
            User user = userOptional.get();
            joinUserUserIds.add(user.getId());
        }

        FoodGameHistory foodGameHistory = FoodGameHistory.builder()
                .roomHistory(roomHistory)
                .unSelectedUserIds(joinUserUserIds) // 방 인원(JoinUser)들의 UserId
                .dateTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")).toString())
                .build();
        foodGameHistoryRepository.save(foodGameHistory);
        return foodGameHistory.getId();
    }

    // 바로 recentFoodGameHistory로 보내도 될 것 같은데,
    public Long getRecentFoodGameId(Long roomId) {
        RoomHistory roomHistory = roomHistoryRepository.findById(roomId).get();
        FoodGameHistory recentFoodGameHistory = foodGameHistoryRepository.findFirstByRoomHistoryOrderByIdDesc(roomHistory).get();
        return recentFoodGameHistory.getId();
    }

    @Transactional
    public StompFoodGameDto receiveFood(StompDto message) {
        // 제출된 음식메뉴를 리스트에 더하고, 선택/미선택 인원 리스트를 갱신합니다.
        Long foodGameHistoryId = getRecentFoodGameId(Long.parseLong(message.getRoomId()));

        FoodGameHistory foodGameHistory = foodGameHistoryRepository.findById(foodGameHistoryId).get();
        List<FoodGameHistory.submitUserInfo> selectedUserInfos = foodGameHistory.getSelectedUserInfos();
        List<Long> unSelectedUserIds = foodGameHistory.getUnSelectedUserIds();

        FoodGameHistory.submitUserInfo submitUserInfo = new FoodGameHistory.submitUserInfo();
        Long Userid = Long.parseLong(message.getSender_id());
        submitUserInfo.setUserId(Userid); // userid
        submitUserInfo.setSubmittedFood(message.getMessage()); // 제출된 음식

        selectedUserInfos.add(submitUserInfo); // 을 묶어 저장
        unSelectedUserIds.remove(Userid); // 미선택은 userid만

        foodGameHistory.setSelectedUserInfos(selectedUserInfos);
        foodGameHistory.setUnSelectedUserIds(unSelectedUserIds);

        foodGameHistoryRepository.save(foodGameHistory); // 여기까지가 제출에 따른 db 정보 갱신

        // 선택 인원은 각각 [닉네임, 프로필이미지, 제출 음식]로 반환.
        // 미선택 인원은 각각 [닉네임, 프로필이미지]로 반환.
        List<List<String>> responseSelectedUserInfos = new ArrayList<>();
        List<List<String>> responseUnSelectedUserInfos = new ArrayList<>();

        for (FoodGameHistory.submitUserInfo selectedUserInfo : selectedUserInfos) {
            List<String> responseSelectedUserInfo = new ArrayList<>();

            User user = userRepository.findById(selectedUserInfo.getUserId()).get();
            responseSelectedUserInfo.add(user.getNickName()); // nickname
            responseSelectedUserInfo.add(user.getProfileImage()); // profileImage
            responseSelectedUserInfo.add(selectedUserInfo.getSubmittedFood()); // 제출음식

            responseSelectedUserInfos.add(responseSelectedUserInfo);
        }
        for (Long unSelectedUserId : unSelectedUserIds) {
            List<String> responseUnSelectedUserInfo = new ArrayList<>();

            User user = userRepository.findById(unSelectedUserId).get();
            responseUnSelectedUserInfo.add(user.getNickName()); // nickname
            responseUnSelectedUserInfo.add(user.getProfileImage()); // profileImage

            responseUnSelectedUserInfos.add(responseUnSelectedUserInfo);
        }

        StompFoodGameDto response = StompFoodGameDto.builder()
                .sender_id(message.getSender_id())
                .roomId(message.getRoomId())
                .selectedUserInfos(responseSelectedUserInfos)
                .unSelectedUserInfos(responseUnSelectedUserInfos)
                .build();
        return response;
    }

    // userId로 roomId에 해당하는 방의 joinUserId 찾기
    public Long findJoinUserId(Long roomId, Long userId) {
        System.out.println("FoodGameService.findJoinUserId");
        RoomHistory roomHistory = roomHistoryRepository.findById(roomId).get();
        List<JoinUser> joinUsers = roomHistory.getJoinUser();

        for (JoinUser joinUser : joinUsers) {
            if (joinUser.getUserId().equals(userId)) {
                return joinUser.getId();
            }
        }
        System.out.println("FoodGameService.findJoinUserId 실행에 있어, 해당하는 joinUser의 UserId를 찾지 못함");
        return null;
    }

    // 가장 최근 음식게임기록에 선정된 음식 기록
    @Transactional
    public void foodSelected(StompDto message) {
        Long foodGameHistoryId = getRecentFoodGameId(Long.parseLong(message.getRoomId()));
        FoodGameHistory foodGameHistory = foodGameHistoryRepository.findById(foodGameHistoryId).get();

        foodGameHistory.setFoodName(message.getMessage());
        foodGameHistoryRepository.save(foodGameHistory);
    }

    // 모든 음식 게임 기록
    public List<FoodResponseDto> getAllFoodGameHistories(Long roomId) {
        RoomHistory roomHistory = roomHistoryRepository.findById(roomId).get();
        List<FoodGameHistory> foodGameHistoryList = roomHistory.getFoodGameHistories();
        List<FoodResponseDto> info = new ArrayList<>();

        String imageRoute;

        for (FoodGameHistory foodGameHistory : foodGameHistoryList) {
            List<String> foodImageList = new ArrayList<>();
            if (!foodGameHistory.getImages().isEmpty()) {
                for (FoodImage image : foodGameHistory.getImages()) {
                    imageRoute = "/" + roomHistory.getId() + "/food/" + image.getId().toString() + ".jpg";
                    foodImageList.add(imageRoute);
                }
            }
            FoodResponseDto foodResponseDto = FoodResponseDto.builder()
                    .id(foodGameHistory.getId())
                    .dateTime(foodGameHistory.getDateTime())
                    .selectedUsers(foodGameHistory.getSelectedUserInfos())
                    .unselectedUsers(foodGameHistory.getUnSelectedUserIds())
                    .foodCandidates(foodGameHistory.getFoodCandidates())
                    .foodName(foodGameHistory.getFoodName())
                    .images(foodImageList)
                    .build();
            info.add(foodResponseDto);
        }
        return info;
    }

    // 한 음식 게임 기록
    public FoodResponseDto getFoodGameHistory(Long contentId) {
        FoodGameHistory foodGameHistory = foodGameHistoryRepository.findById(contentId).get();
        RoomHistory roomHistory = roomHistoryRepository.findById(foodGameHistory.getRoomHistory().getId()).get();

        String imageRoute;

        List<String> foodImageList = new ArrayList<>();
        if (!foodGameHistory.getImages().isEmpty()) {
            for (FoodImage image : foodGameHistory.getImages()) {
                imageRoute = "/" + roomHistory.getId() + "/food/" + image.getId().toString() + ".jpg";
                foodImageList.add(imageRoute);
            }
        }

        FoodResponseDto info = FoodResponseDto.builder()
                .id(foodGameHistory.getId())
                .dateTime(foodGameHistory.getDateTime())
                .selectedUsers(foodGameHistory.getSelectedUserInfos())
                .unselectedUsers(foodGameHistory.getUnSelectedUserIds())
                .foodCandidates(foodGameHistory.getFoodCandidates())
                .foodName(foodGameHistory.getFoodName())
                .images(foodImageList)
                .build();
        return info;
    }
}
