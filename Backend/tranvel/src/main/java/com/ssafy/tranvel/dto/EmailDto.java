package com.ssafy.tranvel.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmailDto {
    @NotEmpty
    private String email;
    private String verificationCode;
}
