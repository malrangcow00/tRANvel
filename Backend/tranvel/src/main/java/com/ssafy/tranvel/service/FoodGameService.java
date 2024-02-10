package com.ssafy.tranvel.service;

import com.ssafy.tranvel.dto.StompFoodGameDto;
import com.ssafy.tranvel.dto.StompFoodSubmitDto;
import com.ssafy.tranvel.entity.FoodGameHistory;
import com.ssafy.tranvel.entity.JoinUser;
import com.ssafy.tranvel.entity.RoomHistory;
import com.ssafy.tranvel.entity.User;
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
import java.util.Random;

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

    @Transactional
    public StompFoodGameDto receiveFood(StompFoodSubmitDto message) {

        // 제출된 음식메뉴를 리스트에 더하고, 선택/미선택 인원 리스트를 갱신합니다.
        Long foodGameHistoryId = Long.parseLong(message.getFoodGameHistoryId());
        Long joinUserId = findJoinUserId(Long.parseLong(message.getRoomId()), Long.parseLong(message.getSender_id()));
        String submittedFood = message.getFood();

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

        // 선택/미선택 인원의 닉네임 정보로 반환합니다.
        List<String> responseSelectedNicknames = new ArrayList<>();
        List<String> responseUnSelectedNicknames = new ArrayList<>();

        for (Long selectedUserId : selectedUsers) {
            JoinUser joinUser = joinUserRepository.findById(selectedUserId).get();
            User user = userRepository.findById(joinUser.getUserId()).get();
            String selectedUserNickname = user.getNickName();
            responseSelectedNicknames.add(selectedUserNickname);
        }
        for (Long unSelectedUserId : unSelectedUsers) {
            JoinUser joinUser = joinUserRepository.findById(unSelectedUserId).get();
            User user = userRepository.findById(joinUser.getUserId()).get();
            String unSelectedUserNickname = user.getNickName();
            responseUnSelectedNicknames.add(unSelectedUserNickname);
        }

        StompFoodGameDto response = StompFoodGameDto.builder()
                .sender_id(message.getSender_id())
                .roomId(message.getRoomId())
                .selectedUserNicknames(responseSelectedNicknames)
                .unSelectedUserNicknames(responseUnSelectedNicknames)
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

    public String getFood(Long foodGameHistoryId) {
        FoodGameHistory foodGameHistory = foodGameHistoryRepository.findById(foodGameHistoryId).get();
        List<String> foodCandidates = foodGameHistory.getFoodCandidates();

        Random random = new Random();
        int foodCandidatesSize = foodCandidates.size();
        int randomIndex = random.nextInt(foodCandidatesSize);
        String selectedFood = foodCandidates.get(randomIndex);

        foodGameHistory.setFoodName(selectedFood);
        foodGameHistoryRepository.save(foodGameHistory);

        return selectedFood;
    }
}
