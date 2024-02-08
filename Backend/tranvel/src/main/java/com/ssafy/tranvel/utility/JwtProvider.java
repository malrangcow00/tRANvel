package com.ssafy.tranvel.utility;
import com.ssafy.tranvel.dto.TokenDto;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class JwtProvider {
    private final Key key;
    @Autowired
    public JwtProvider(
            @Value("${jwt.secretKey}") String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
    public TokenDto generateTokens(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + 86400000); // Access Token 1일
        Date refreshTokenExpiresIn = new Date(now + 1728000000); // Refresh Token 20일
//        Date accessTokenExpiresIn = new Date(now + 10000); // Access Token 10초 (임시)
//        Date refreshTokenExpiresIn = new Date(now + 20000); // Refresh Token 20초 (임시)
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .claim("token_type", "access")
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .claim("token_type", "refresh")
                .setExpiration(refreshTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        return new TokenDto("Bearer", accessToken, refreshToken);
    }
    public Authentication getAuthenticationFromToken(String token) {
        Claims claims = parseClaims(token);
        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
    public boolean validateToken(String token, String expectedType) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String tokenType = claims.get("token_type", String.class);
            return expectedType.equals(tokenType);
        } catch (ExpiredJwtException e) {
            if ("refresh".equals(expectedType)) {
                String tokenType = e.getClaims().get("token_type", String.class);
                return "refresh".equals(tokenType);
            }
            log.info("Expired JWT Token", e);
            return false;
        } catch (Exception e) {
            log.info("Invalid JWT Token", e);
            return false;
        }
    }
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
    public UserDetails getUserDetailsFromToken(String token) {
        Claims claims = parseClaims(token);
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        return new User(claims.getSubject(), "", authorities);
    }
    public boolean validateTokenWithoutExpiration(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return false; // Token is valid and not expired
        } catch (ExpiredJwtException e) {
            return true; // Token is expired
        } catch (Exception e) {
            log.info("Invalid or malformed JWT Token", e);
            return false; // Token is invalid for other reasons
        }
    }
}
