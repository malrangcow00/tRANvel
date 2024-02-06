package com.ssafy.tranvel.repository;

import com.ssafy.tranvel.entity.RandomGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RandomGameRepository extends JpaRepository<RandomGame, Long> {
}
