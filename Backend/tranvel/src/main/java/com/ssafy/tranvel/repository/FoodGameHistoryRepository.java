package com.ssafy.tranvel.repository;

import com.ssafy.tranvel.entity.FoodGameHistory;
import com.ssafy.tranvel.entity.RoomHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodGameHistoryRepository extends JpaRepository<FoodGameHistory, Long> {
    Optional<FoodGameHistory> findById(Long contentId);

    // 해당 RoomHistory에 속하는 FoodGameHistory 중, Id 값이 가장 높은(최근) FoodGameHistory를 찾아 반환
    Optional<FoodGameHistory> findFirstByRoomHistoryOrderByIdDesc(RoomHistory roomHistory);
}
