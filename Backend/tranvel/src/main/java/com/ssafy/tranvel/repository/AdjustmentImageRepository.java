package com.ssafy.tranvel.repository;

import com.ssafy.tranvel.entity.AdjustmentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdjustmentImageRepository extends JpaRepository<AdjustmentImage, Long> {
}
