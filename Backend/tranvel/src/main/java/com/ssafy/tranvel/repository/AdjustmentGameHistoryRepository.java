package com.ssafy.tranvel.repository;

import com.ssafy.tranvel.entity.AdjustmentGameHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdjustmentGameHistoryRepository extends JpaRepository<AdjustmentGameHistory, Long> {

}
