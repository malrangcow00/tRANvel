package com.ssafy.tranvel.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class UserUpdateDto {

    private String nickName;

    private String  password1;

    private String password2;

}
