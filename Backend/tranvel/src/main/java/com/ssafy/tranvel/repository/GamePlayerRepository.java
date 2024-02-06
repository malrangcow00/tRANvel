package com.ssafy.tranvel.repository;

import com.ssafy.tranvel.entity.GamePlayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamePlayerRepository extends JpaRepository<GamePlayer, Long> {
}
