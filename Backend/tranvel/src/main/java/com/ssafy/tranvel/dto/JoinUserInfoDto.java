package com.ssafy.tranvel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JoinUserInfoDto {

    private Long joinUserId;

    private String nickName;

    private String profileImage;
}
