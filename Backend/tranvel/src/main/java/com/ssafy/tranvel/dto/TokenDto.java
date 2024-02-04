package com.ssafy.tranvel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

// 로그인 성공 시, JWT를 반환하는 DTO

@Builder
@Data
@AllArgsConstructor
public class TokenDto {

    private String grantType; // prefix: Bearer
    private String accessToken;
    private String refreshToken;

}
