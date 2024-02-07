package com.ssafy.tranvel.security;

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
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String accessToken = resolveToken(httpRequest, "Access-Token");
        String refreshToken = resolveToken(httpRequest, "Refresh-Token");
        String requestUri = httpRequest.getRequestURI();

        try {
//            if (isAllowedPath(requestUri)) {
//                chain.doFilter(request, response);
//                return;
//            }

            if ("/user/token/refresh".equals(requestUri)) {
                if (refreshToken != null && jwtProvider.validateToken(refreshToken, "refresh")) {
                    chain.doFilter(request, response);
                } else {
                    sendUnauthorizedResponse(httpResponse, "Refresh Token이 유효하지 않거나 만료되었습니다. 다시 로그인해주세요.");
                }
            } else {
                if (accessToken != null && jwtProvider.validateToken(accessToken, "access")) {
                    Authentication authentication = jwtProvider.getAuthenticationFromToken(accessToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    chain.doFilter(request, response);
                } else {
                    sendUnauthorizedResponse(httpResponse, "Access Token이 유효하지 않습니다. 다시 로그인해주세요.");
                }
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            log.error("Authentication error: ", e);
            sendUnauthorizedResponse(httpResponse, "Authentication error: " + e.getMessage());
        }
    }

//    private boolean isAllowedPath(String requestUri) {
//        List<String> allowedPaths = Arrays.asList("/signup", "/email-auth", "/email-auth/verification", "/user/duplication", "/user/signin", "/swagger-ui/", "/v3/", "/api/");
//        return allowedPaths.stream().anyMatch(path -> requestUri.startsWith(path));
//    }

    private String resolveToken(HttpServletRequest request, String headerName) {
        String prefixToken = request.getHeader(headerName);
        if (StringUtils.hasText(prefixToken) && prefixToken.startsWith("Bearer ")) {
            return prefixToken.substring(7);
        }
        return null;
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(message);
    }
}
