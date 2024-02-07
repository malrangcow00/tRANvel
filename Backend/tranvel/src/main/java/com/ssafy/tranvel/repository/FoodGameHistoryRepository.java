package com.ssafy.tranvel.repository;

import com.ssafy.tranvel.entity.FoodGameHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodGameHistoryRepository extends JpaRepository<FoodGameHistory, Long> {
    Optional<FoodGameHistory> findById(Long contentId);
}
