package com.ssafy.tranvel.controller;

import com.ssafy.tranvel.dto.TokenDto;
import com.ssafy.tranvel.utility.JwtProvider;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class AuthController {

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthController(JwtProvider jwtProvider, UserDetailsService userDetailsService) {
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
    }

    private String resolveTokenFromHeader(HttpServletRequest request, String headerName) {
        String bearerToken = request.getHeader(headerName);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<?> regenerateAccessToken(HttpServletRequest request) {
        String accessToken = resolveTokenFromHeader(request, "Access-Token");
        String refreshToken = resolveTokenFromHeader(request, "Refresh-Token");

        try {
            if (!jwtProvider.validateToken(refreshToken, "refresh")) {
                return ResponseEntity
                        .badRequest()
                        .body("Error: Invalid or expired refresh token.");
            }

            if (!jwtProvider.validateToken(accessToken, "access") && !jwtProvider.validateTokenWithoutExpiration(accessToken)) {
                return ResponseEntity
                        .badRequest()
                        .body("Error: Invalid Access Token.");
            }

            // Generate new Access Token
            UserDetails userDetails = jwtProvider.getUserDetailsFromToken(refreshToken);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            TokenDto newAccessToken = jwtProvider.generateTokens(authentication);

            return ResponseEntity.ok(newAccessToken);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }
}
