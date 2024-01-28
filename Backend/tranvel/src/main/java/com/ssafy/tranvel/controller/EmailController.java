package com.ssafy.tranvel.controller;


import com.ssafy.tranvel.dto.EmailDto;
import com.ssafy.tranvel.service.EmailAuthService;
import jakarta.mail.MessagingException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Getter @Setter
@RequiredArgsConstructor
@RestController
public class EmailController {

    private final EmailAuthService emailAuthService;

    @PostMapping("/email")
    public void sendEmail(@RequestBody @Validated EmailDto emailDto) throws MessagingException, UnsupportedEncodingException {
        emailAuthService.createEmailForm(emailDto.getEmail());
    }


     /**
     해당 controller 수정 예정
     emailAuthService.verifyEmail() 의 리턴값에 따른 sign up 권한 부여
     redis 에 저장 예정 중 입니다. 변경 가능성 있습니다.
      **/
    @GetMapping("/verify")
    public String verifyCode(@RequestBody @Validated EmailDto emailDto) {
        return emailAuthService.verifyEmail(emailDto.getEmail(), emailDto.getVerificationCode());

    }
}
