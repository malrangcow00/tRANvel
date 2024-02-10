package com.ssafy.tranvel.service;

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

    public Long startFoodGame(Long roomId) {
        System.out.println("FoodGameService.startFoodGame");
        RoomHistory roomHistory = roomHistoryRepository.findById(roomId).get();
        List<JoinUser> joinUsers = roomHistory.getJoinUser();
        List<Long> joinUserIds = new ArrayList<>(); // UserId 아닌, JoinUserId

        for (JoinUser joinUser : joinUsers) {
            Optional<User> userOptional = userRepository.findById(joinUser.getUserId());
            if (!userOptional.isPresent()) {
                continue;
            }
            User user = userOptional.get();
            joinUserIds.add(user.getId());
        }

        FoodGameHistory foodGameHistory = FoodGameHistory.builder()
                .roomHistory(roomHistory)
                .unselectedUsers(joinUserIds) // 방 인원들의 JoinUserId
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
        Long joinUserId = findJoinUserId(Long.parseLong(message.getRoomId()), Long.parseLong(message.getSender_id()));
        String submittedFood = message.getMessage();

        FoodGameHistory foodGameHistory = foodGameHistoryRepository.findById(foodGameHistoryId).get();
        List<String> foodCandidates = foodGameHistory.getFoodCandidates();
        List<Long> selectedUsers = foodGameHistory.getSelectedUsers();
        List<Long> unSelectedUsers = foodGameHistory.getUnselectedUsers();

        foodCandidates.add(submittedFood);
        selectedUsers.add(joinUserId);
        unSelectedUsers.remove(joinUserId);

        foodGameHistory.setFoodCandidates(foodCandidates);
        foodGameHistory.setSelectedUsers(selectedUsers);
        foodGameHistory.setUnselectedUsers(unSelectedUsers);

        foodGameHistoryRepository.save(foodGameHistory);

        // 선택/미선택 인원의 프로필 사진으로 반환합니다.
        List<String> responseSelectedProfileImages = new ArrayList<>();
        List<String> responseUnSelectedProfileImages = new ArrayList<>();

        for (Long selectedUserId : selectedUsers) {
            JoinUser joinUser = joinUserRepository.findById(selectedUserId).get();
            User user = userRepository.findById(joinUser.getUserId()).get();
            String selectedUserProfileImages = user.getProfileImage();
            responseSelectedProfileImages.add(selectedUserProfileImages);
        }
        for (Long unSelectedUserId : unSelectedUsers) {
            JoinUser joinUser = joinUserRepository.findById(unSelectedUserId).get();
            User user = userRepository.findById(joinUser.getUserId()).get();
            String unSelectedUserProfileImages = user.getProfileImage();
            responseUnSelectedProfileImages.add(unSelectedUserProfileImages);
        }

        StompFoodGameDto response = StompFoodGameDto.builder()
                .sender_id(message.getSender_id())
                .roomId(message.getRoomId())
                .selectedUserProfileImages(responseSelectedProfileImages)
                .unSelectedUserProfileImages(responseUnSelectedProfileImages)
                .foodCandidates(foodCandidates)
                .build();
        return response;
    }

    // userId로 roomId에 해당하는 방의 joinUserId 찾기
    public Long findJoinUserId(Long roomId, Long userId) {
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
    public void foodSelected(StompDto message) {
        Long foodGameHistoryId = getRecentFoodGameId(Long.parseLong(message.getRoomId()));
        FoodGameHistory foodGameHistory = foodGameHistoryRepository.findById(foodGameHistoryId).get();

        foodGameHistory.setFoodName(message.getMessage());
        foodGameHistoryRepository.save(foodGameHistory);
    }

    // 모든 음식 게임 기록
    public List<FoodGameHistory> getAllFoodGameHistories(Long roomId) {
        RoomHistory roomHistory = roomHistoryRepository.findById(roomId).get();

        return roomHistory.getFoodGameHistories();
    }

    // 한 음식 게임 기록
    public FoodGameHistory getFoodGameHistory(Long contentId) {
        return foodGameHistoryRepository.findById(contentId).get();
    }
}
