package com.ssafy.tranvel.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserSignInDto {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
