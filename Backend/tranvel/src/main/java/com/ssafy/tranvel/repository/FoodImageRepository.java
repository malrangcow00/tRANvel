package com.ssafy.tranvel.repository;

import com.ssafy.tranvel.entity.FoodImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodImageRepository extends JpaRepository<FoodImage, Long> {
}
