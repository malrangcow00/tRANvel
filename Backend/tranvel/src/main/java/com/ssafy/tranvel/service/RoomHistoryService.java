package com.ssafy.tranvel.service;

import com.ssafy.tranvel.dto.RoomHistoryDto;
import com.ssafy.tranvel.entity.JoinUser;
import com.ssafy.tranvel.entity.RoomHistory;
import com.ssafy.tranvel.entity.User;
import com.ssafy.tranvel.repository.JoinUserRepository;
import com.ssafy.tranvel.repository.RoomHistoryRepository;
import com.ssafy.tranvel.repository.UserRepository;
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
        User user = userRepository.findById(roomHistoryDto.getUserId()).get();
//        JoinUser joinUser = joinUserRepository.findByUserId(roomHistoryDto.getUserId()).get();

//        return roomHistoryRepository.findByJoinUser(user.getJoinUser());
        return user.getRoomHistories();
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


    public void addJoinUser(Long userId, String roomCode, String inputRoomPassword) {

        RoomHistory roomHistory = roomHistoryRepository.findByRoomCode(roomCode).get();
        if (roomHistory.getRoomPassword().equals(inputRoomPassword)) {
            User user = userRepository.findById(userId).get();
            JoinUser joinUser = JoinUser.builder()
                    .authority(roomHistory.getJoinUser() == null)
                    .userId(userId)
                    .roomHistory(roomHistory)
                    .build();

            System.out.println(roomHistory.getJoinUser() == null);
            if (roomHistory.getJoinUser() == null) {
                List<JoinUser> nowJoin = new ArrayList<>();
                nowJoin.add(joinUser);
                roomHistory.joinUser(nowJoin);
                joinUserRepository.save(joinUser);
            } else {
                List<JoinUser> nowJoin = roomHistory.getJoinUser() == null? null: roomHistory.getJoinUser();

                boolean isCheck = false;
                for (int idx = 0; idx < nowJoin.size(); idx ++) {
                    if (
                            Objects.equals(nowJoin.get(idx).getUserId().toString(),userId.toString()))
                    {
                        isCheck = true;
                        break;
                    }
                }

                if (!isCheck) {
                    nowJoin.add(joinUser);
                    joinUserRepository.save(joinUser);
                    roomHistory.joinUser(nowJoin);

                }

            }
        }

        // 반환 형태 고려



    }

    public RoomHistory createRoomHistory(RoomHistoryDto roomHistoryDto) {
        System.out.println(roomHistoryDto.getUserId());
        System.out.println(roomHistoryDto.getRoomPassword());
        User user = userRepository.findById(roomHistoryDto.getUserId()).get();
        String roomCode = createRoomCode();
        RoomHistory roomHistory = RoomHistory.builder()
                .user(user)
                .roomCode(roomCode)
                // 암호화
                .roomPassword(roomHistoryDto.getRoomPassword())
                .startDate(LocalDateTime.now(ZoneId.of("Asia/Seoul")).toString())
                .balanceResult(0)
                .nowPlaying(true)
                .joinUser(null)
                .build();
        roomHistoryRepository.save(roomHistory);
        RoomHistory roomHistory1 = roomHistoryRepository.findByRoomCode(roomCode).get();
        addJoinUser(roomHistoryDto.getUserId(), roomCode, roomHistoryDto.getRoomPassword());

        return roomHistory1;
    }


    public RoomHistory getRoomDetailHistory(RoomHistoryDto roomHistoryDto) {
        RoomHistory roomHistory = roomHistoryRepository.findById(roomHistoryDto.getRoomId()).get();

        return roomHistory;
    }

    // 히스토리 수정은 후순위



    public void finishRoomHistory(RoomHistoryDto roomHistoryDto) {
        roomHistoryRepository.findById(roomHistoryDto.getRoomId()).get().finishRoom();
    }


    public Long deleteRoomHistory(RoomHistoryDto roomHistoryDto) {
        roomHistoryRepository.deleteById(roomHistoryDto.getRoomId());
        return roomHistoryDto.getRoomId();
    }
}
