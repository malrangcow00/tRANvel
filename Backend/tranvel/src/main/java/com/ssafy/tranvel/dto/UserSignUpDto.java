package com.ssafy.tranvel.dto;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserSignUpDto {

    @NotEmpty
    private String email;

    @NotEmpty
    private String nickName;

    @NotEmpty
    private String password;

    private String profileImage;

    private int balance;
}
