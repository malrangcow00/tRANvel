package com.ssafy.tranvel.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


// API 호출 시, 헤더에 어떤 User가 API를 요청했는지 조회하는 코드 (현재 UserId = email를 조회)
public class SecurityUtility {
    public static String getCurrentUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("No authentication information.");
        }
        return authentication.getName();
    }


}