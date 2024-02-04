package com.ssafy.tranvel.security;

import com.ssafy.tranvel.dto.TokenDto;
import com.ssafy.tranvel.utility.JwtProvider;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String token = resolveToken(httpRequest);
        String requestUri = httpRequest.getRequestURI();

        try {
            if (isAllowedPath(requestUri)) {
                // 허용된 경로인 경우 필터 체인 진행
                chain.doFilter(request, response);
                return;
            }

            if (token != null && jwtProvider.isTokenInBlackList(token)) {
                // 블랙리스트 토큰인 경우 인증 실패 처리
                sendUnauthorizedResponse(httpResponse, "이 토큰은 사용할 수 없습니다.");
                return;
            }

            if (token != null && jwtProvider.validateToken(token)) {
                // 토큰 유효한 경우
                Authentication authentication = jwtProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(request, response);
            } else if (token != null) {
                // 토큰 만료된 경우, 리프레시 토큰으로 새 토큰 발급
                String refreshToken = getRefreshToken(httpRequest);
                if (refreshToken != null && jwtProvider.validateRefreshToken(refreshToken)) {
                    UserDetails userDetails = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
                    TokenDto newTokens = jwtProvider.regenerateToken(refreshToken, userDetails);
                    sendNewTokens(httpResponse, newTokens);
                    jwtProvider.addToBlackList(token); // 새 토큰 발급 후 이전 토큰 블랙리스트 처리
                } else {
                    sendUnauthorizedResponse(httpResponse, "Refresh 토큰이 유효하지 않습니다.");
                }
            } else {
                sendUnauthorizedResponse(httpResponse, "토큰이 제공되지 않았습니다.");
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            log.error("인증 오류: ", e); // 오류 로깅 추가
//            sendUnauthorizedResponse(httpResponse, "인증 오류 발생");
            sendUnauthorizedResponse(httpResponse, "인증 오류 발생: " + e.getMessage()); // 예외 메시지 포함
        }
    }

    private boolean isAllowedPath(String requestUri) {
        // 허용된 경로 리스트
        List<String> allowedPaths = Arrays.asList("/signup", "/email-auth", "/user/duplication/", "/user/signin");
//        return allowedPaths.contains(requestUri) || requestUri.startsWith("/swagger") || requestUri.isEmpty();
        return allowedPaths.stream().anyMatch(requestUri::startsWith);

    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Header prefix 제거
        }
        return null;
    }

    private String getRefreshToken(HttpServletRequest request) {
        return request.getHeader("Refresh-Token");
    }

    private void sendNewTokens(HttpServletResponse response, TokenDto newTokens) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("Access Token: " + newTokens.getAccessToken() + ", Refresh Token: " + newTokens.getRefreshToken());
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(message);
    }
}
