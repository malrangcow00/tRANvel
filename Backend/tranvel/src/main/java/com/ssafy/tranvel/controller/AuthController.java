package com.ssafy.tranvel.controller;

import com.ssafy.tranvel.dto.TokenDto;
import com.ssafy.tranvel.repository.RefreshTokenRepository;
import com.ssafy.tranvel.utility.JwtProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class AuthController {

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public AuthController(JwtProvider jwtProvider, UserDetailsService userDetailsService, RefreshTokenRepository refreshTokenRepository) {
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestParam("refreshToken") String refreshToken) {
        // Refresh Token이 DB에 저장된 것인지 확인
        if (refreshTokenRepository.findByToken(refreshToken) == null) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Refresh token is not in the database.");
        }

        if (!jwtProvider.validateToken(refreshToken)) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Invalid refresh token.");
        }

        // 연관된 사용자 정보 가져오기
        String username = refreshTokenRepository.findByToken(refreshToken).getUsername();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        // 새로운 Access Token 생성
        TokenDto newAccessToken = jwtProvider.generateAccessToken(authentication);

        return ResponseEntity.ok(newAccessToken);
    }
}
