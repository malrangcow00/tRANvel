package com.ssafy.tranvel.repository;

import com.ssafy.tranvel.entity.JoinUser;
import com.ssafy.tranvel.entity.RoomHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomHistoryRepository extends JpaRepository<RoomHistory, Long> {
//    Optional<List<RoomHistory>> findByJoinUser(List<JoinUser> joinUser);

    Optional<RoomHistory> findByRoomCode(String roomCode);
}
