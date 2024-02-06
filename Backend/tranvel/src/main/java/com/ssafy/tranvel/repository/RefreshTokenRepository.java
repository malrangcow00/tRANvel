package com.ssafy.tranvel.repository;

import com.ssafy.tranvel.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    // Token 값으로 RefreshToken 엔티티 검색
    RefreshToken findByToken(String refreshToken);
}
