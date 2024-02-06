package com.ssafy.tranvel.utility;

import com.ssafy.tranvel.dto.TokenDto;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

//import io.jsonwebtoken.io.Decoders; // 256bit
import java.nio.charset.StandardCharsets; // 512bit
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class JwtProvider {

    private final Key key;


    @Autowired
    public JwtProvider(
            @Value("${jwt.secret}") String secretKey) {
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey); // 256bit
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8); // 512bit
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // Access Token 생성
        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + 1800000); // 30분
        Date refreshTokenExpiresIn = new Date(now + 86400000); // 1일

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities) // 권한 정보
                .setExpiration(accessTokenExpiresIn)
//                .signWith(key, SignatureAlgorithm.HS256) // 256bit
                .signWith(key, SignatureAlgorithm.HS512) // 512bit
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(refreshTokenExpiresIn) // 1일
//                .signWith(key, SignatureAlgorithm.HS256) // 256bit
                .signWith(key, SignatureAlgorithm.HS512) // 512bit
                .compact();

        // 인증 타입 prefix 설정 ("Bearer")
        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // JWT 복호화 및 정보 추출
    public Authentication getAuthentication(String accessToken) {

        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // Claim 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // JWT 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // Refresh Token 유효성 검증
    public boolean validateRefreshToken(String token) {
        return validateToken(token); // 기존의 validateToken 메서드 재사용
    }

    // 새로운 Access Token과 Refresh Token 발급
    public TokenDto regenerateToken(String refreshToken, UserDetails userDetails) {
        // Refresh Token 검증
        if (!validateRefreshToken(refreshToken)) {
            throw new RuntimeException("Refresh 토큰이 유효하지 않습니다.");
        }

        // 새로운 토큰 생성
        return generateToken(new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), null, userDetails.getAuthorities()));
    }
}
