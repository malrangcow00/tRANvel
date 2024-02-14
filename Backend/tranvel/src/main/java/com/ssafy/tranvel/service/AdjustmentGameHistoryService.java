package com.ssafy.tranvel.service;

import com.ssafy.tranvel.dto.*;
import com.ssafy.tranvel.entity.AdjustmentGameHistory;
import com.ssafy.tranvel.entity.AdjustmentImage;
import com.ssafy.tranvel.entity.RandomGame;
import com.ssafy.tranvel.entity.RoomHistory;
import com.ssafy.tranvel.repository.AdjustmentGameHistoryRepository;
import com.ssafy.tranvel.repository.RandomGameRepository;
import com.ssafy.tranvel.repository.RoomHistoryRepository;
import com.ssafy.tranvel.entity.*;
import com.ssafy.tranvel.repository.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    private final ImageUploadService imageUploadService;

//    방 아이디를 받아서 이 방에 참가중인 유저들의[JoinUserId, Nickname, ProfileImage] 리스트를 반환(선택할 리스트 제공)
//    JoinUserId : JoinUser의 id O / userId(User 상 Id) X
    @Transactional
    public List<JoinUserInfoDto> getJoinUsers(Long roomId) {
        System.out.println("AdjustmentGameHistoryService.getJoinUsers");
        RoomHistory roomHistory = roomHistoryRepository.findById(roomId).get();
        List<JoinUser> joinUsers = roomHistory.getJoinUser();
        List<JoinUserInfoDto> joinUserReturn = new ArrayList<>();

        for (JoinUser joinUser : joinUsers) {
            Optional<User> userOptional = userRepository.findById(joinUser.getUserId());
            if (!userOptional.isPresent()) {
                continue;
            }
            User user = userOptional.get();
            JoinUserInfoDto joinUserInfoDto = new JoinUserInfoDto(joinUser.getId(), user.getNickName(), user.getProfileImage());
            joinUserReturn.add(joinUserInfoDto);
        }
        System.out.println("AdjustmentGameHistoryService.getJoinUsers Ready");
        return joinUserReturn;
    }

    // 정산 정보 입력 시 N등분 해서 정산 실시.
    // selectedUsers의 아이디는 User.Id가 아닌, joinUser.Id
    @Transactional
    public int adjustment(AdjustmentGameHistoryDto adjustmentGameHistoryDto, MultipartFile image) {
        System.out.println("AdjustmentGameHistoryService.adjustment");
        RoomHistory roomHistory = roomHistoryRepository.findById(adjustmentGameHistoryDto.getRoomId()).get();
//        Long miniGameCodeId = adjustmentGameHistoryDto.getMiniGameCodeId();
//        if (miniGameCodeId != null) {
//            RandomGame randomGame = randomGameRepository.findById(miniGameCodeId).get();
//        } // 일반정산이라 주석처리

        List<Long> selectedUsers = adjustmentGameHistoryDto.getSelectedUsers();
        int moneyResult = adjustmentGameHistoryDto.getPrice()/selectedUsers.size();

        for (Long selectedUser:selectedUsers) {

            JoinUser joinUser = joinUserRepository.findById(selectedUser).get();
            joinUser.setProfit(joinUser.getProfit() + moneyResult);  // User 에서 moneyResult 반영
            joinUserRepository.save(joinUser);

            Optional<User> userOptional = userRepository.findById(joinUser.getUserId());
            if (!userOptional.isPresent()) { // selectedUser에서 이미 살아있는 유저만 걸러오기 때문에 없어도 괜찮을지도
                continue;
            }

            User user = userOptional.get();
            user.setBalance(user.getBalance() + moneyResult);
            userRepository.save(user); // User 에서 moneyResult 반영
        }

        AdjustmentGameHistory adjustmentGameHistory = AdjustmentGameHistory.builder()
                .roomHistory(roomHistory)
//                .miniGameCode(randomGame) 일반정산
//                .targetUser() // 일반정산
                .dateTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")).toString())
                .selectedUsers(adjustmentGameHistoryDto.getSelectedUsers())
                .price(adjustmentGameHistoryDto.getPrice())
                .moneyResult(moneyResult)
                .category(adjustmentGameHistoryDto.getCategory())
                .detail(adjustmentGameHistoryDto.getDetail())
                .location(adjustmentGameHistoryDto.getLocation())
                .build();
        Long contentId = adjustmentGameHistoryRepository.save(adjustmentGameHistory).getId();
        ImagePostDto info = new ImagePostDto();
        info.setRoomId(adjustmentGameHistoryDto.getRoomId());
        info.setContentId(contentId);
        try {
            if (image != null) {
                imageUploadService.uploadImage(info, image, "adjustment");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return moneyResult;
    }

    // 모든 정산 게임 기록 열람
    @Transactional
    public List<AdjustmentResponseDto> getAllAdjustmentHistories(Long roomId) {
        System.out.println("AdjustmentGameHistoryService.getAllAdjustmentHistories");
        RoomHistory roomHistory = roomHistoryRepository.findById(roomId).get();
        List<AdjustmentGameHistory> adjustmentGameHistoryList = roomHistory.getAdjustmentGameHistories();

        List<AdjustmentResponseDto> info = new ArrayList<>();
        String imageRoute;

        for (AdjustmentGameHistory adjustmentGameHistory : adjustmentGameHistoryList) {
            List<String> adjustmentImageList = new ArrayList<>();
            if (!adjustmentGameHistory.getImages().isEmpty()) {
                for (AdjustmentImage image : adjustmentGameHistory.getImages()) {
                    imageRoute = "/" + roomHistory.getId() + "/adjustment/" + image.getId().toString() + ".jpg";
                    adjustmentImageList.add(imageRoute);
                }
            }
            AdjustmentResponseDto adjustmentResponseDto = AdjustmentResponseDto.builder()
                    .id(adjustmentGameHistory.getId())
                    .dateTime(adjustmentGameHistory.getDateTime())
                    .price(adjustmentGameHistory.getPrice())
                    .moneyResult(adjustmentGameHistory.getMoneyResult())
                    .selectedUsers(adjustmentGameHistory.getSelectedUsers())
                    .images(adjustmentImageList)
                    .category(adjustmentGameHistory.getCategory())
                    .location(adjustmentGameHistory.getLocation())
                    .build();
            info.add(adjustmentResponseDto);
        }

        Collections.sort(info, (o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
        System.out.println("AdjustmentGameHistoryService.getAllAdjustmentHistories Ready");
        return info;
    }

    // 한 정산 게임 기록 열람
    @Transactional
    public AdjustmentResponseDto getAdjustmentHistory(Long contentId) {
        AdjustmentGameHistory adjustmentGameHistory = adjustmentGameHistoryRepository.findById(contentId).get();
        RoomHistory roomHistory = roomHistoryRepository.findById(adjustmentGameHistory.getRoomHistory().getId()).get();
        String imageRoute;

        List<String> adjustmentImageList = new ArrayList<>();
        if (!adjustmentGameHistory.getImages().isEmpty()) {
            for (AdjustmentImage image : adjustmentGameHistory.getImages()) {
                imageRoute = "/" + roomHistory.getId() + "/adjustment/" + image.getId().toString() + ".jpg";
                adjustmentImageList.add(imageRoute);
            }
        }
        AdjustmentResponseDto adjustmentResponseDto = AdjustmentResponseDto.builder()
                .id(adjustmentGameHistory.getId())
                .dateTime(adjustmentGameHistory.getDateTime())
                .price(adjustmentGameHistory.getPrice())
                .moneyResult(adjustmentGameHistory.getMoneyResult())
                .selectedUsers(adjustmentGameHistory.getSelectedUsers())
                .images(adjustmentImageList)
                .category(adjustmentGameHistory.getCategory())
                .location(adjustmentGameHistory.getLocation())
                .build();

        System.out.println("AdjustmentGameHistoryService.getAdjustmentHistory");
        return adjustmentResponseDto;
    }
}