package com.ssafy.tranvel.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileDto {

    private Long id;

    private String email;

    private String nickName;

    private String profileImage;

    private int balance;

}
