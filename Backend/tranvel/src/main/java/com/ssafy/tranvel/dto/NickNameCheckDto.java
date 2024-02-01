package com.ssafy.tranvel.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NickNameCheckDto {

    @NotEmpty
    private String nickName;

    @NotEmpty
    private String email;

}
