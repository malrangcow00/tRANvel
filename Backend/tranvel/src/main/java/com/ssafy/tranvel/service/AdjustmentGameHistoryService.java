package com.ssafy.tranvel.service;

import com.ssafy.tranvel.dto.AdjustmentGameHistoryDto;
import com.ssafy.tranvel.dto.JoinUserInfoDto;
import com.ssafy.tranvel.dto.RoomHistoryDto;
import com.ssafy.tranvel.entity.*;
import com.ssafy.tranvel.repository.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class AdjustmentGameHistoryService {

    private final AdjustmentGameHistoryRepository adjustmentGameHistoryRepository;
    private final UserRepository userRepository;
    private final JoinUserRepository joinUserRepository;
    private final RoomHistoryRepository roomHistoryRepository;
    private final RandomGameRepository randomGameRepository;

//    방 아이디를 받아서 이 방에 참가중인 유저들의{JoinUserId, Nickname, ProfileImage} 리스트를 반환(선택할 리스트 제공)
//    JoinUserId : JoinUser의 id O / userId(User 상 Id) X
    public List<JoinUserInfoDto> getJoinUsers(AdjustmentGameHistoryDto adjustmentGameHistoryDto) {
        Long roomId = adjustmentGameHistoryDto.getRoomId();
        RoomHistory roomHistory = roomHistoryRepository.findById(roomId).get();
        List<JoinUser> joinUsers = roomHistory.getJoinUser();
        List<JoinUserInfoDto> joinUserReturn = new ArrayList<>();

        for (JoinUser joinUser : joinUsers) {
            User user = userRepository.findById(joinUser.getUserId()).get();
            JoinUserInfoDto joinUserInfoDto = new JoinUserInfoDto(joinUser.getId(), user.getNickName(), user.getProfileImage());
            joinUserReturn.add(joinUserInfoDto);
        }
        return joinUserReturn;
    }

    // 정산 정보 입력 시 N등분 해서 정산 실시.
    // selectedUsers의 아이디는 User.Id가 아닌, joinUser.Id
    @Transactional
    public int adjustment(AdjustmentGameHistoryDto adjustmentGameHistoryDto) {
        System.out.println("AdjustmentGameHistoryService.adjustment"); //

        RoomHistory roomHistory = roomHistoryRepository.findById(adjustmentGameHistoryDto.getRoomId()).get();
//        Long miniGameCodeId = adjustmentGameHistoryDto.getMiniGameCodeId();
//        if (miniGameCodeId != null) {
//            RandomGame randomGame = randomGameRepository.findById(miniGameCodeId).get();
//        } // 일반정산이라 주석처리

        List<Long> selectedUsers = adjustmentGameHistoryDto.getSelectedUsers();
        int moneyResult = adjustmentGameHistoryDto.getPrice()/selectedUsers.size();

        for (Long selectedUser:selectedUsers) {

            JoinUser joinUser = joinUserRepository.findById(selectedUser).get();
            joinUser.setProfit(joinUser.getProfit() - moneyResult);
            joinUserRepository.save(joinUser); // joinUser에서 moneyResult 반영

            User user = userRepository.findById(joinUser.getUserId()).get();
            user.setBalance(user.getBalance() - moneyResult);
            userRepository.save(user); // User 에서 moneyResult 반영
        }

        AdjustmentGameHistory adjustmentGameHistory = AdjustmentGameHistory.builder()
                .roomHistory(roomHistory)
//                .miniGameCode(randomGame) 일반정산
//                .targetUser() // 일반정산
                .dateTime(adjustmentGameHistoryDto.getDateTime())
                .selectedUsers(adjustmentGameHistoryDto.getSelectedUsers())
                .price(adjustmentGameHistoryDto.getPrice())
                .moneyResult(moneyResult)
                .image(adjustmentGameHistoryDto.getImage())
                .category(adjustmentGameHistoryDto.getCategory())
                .detail(adjustmentGameHistoryDto.getDetail())
                .build();
        adjustmentGameHistoryRepository.save(adjustmentGameHistory);

        return moneyResult;
    }

    // 모든 정산 게임 기록
    public List<AdjustmentGameHistory> getAllAdjustmentHistories(AdjustmentGameHistoryDto adjustmentGameHistoryDto) {
        RoomHistory roomHistory = roomHistoryRepository.findById(adjustmentGameHistoryDto.getRoomId()).get();
        List<AdjustmentGameHistory> adjustmentGameHistoryList = roomHistory.getAdjustmentGameHistories();

        return adjustmentGameHistoryList;
    }

    // 한 정산 게임 기록
    public AdjustmentGameHistory getAdjustmentHistory(AdjustmentGameHistoryDto adjustmentGameHistoryDto) {
        return adjustmentGameHistoryRepository.findById(adjustmentGameHistoryDto.getId()).get();
    }
}