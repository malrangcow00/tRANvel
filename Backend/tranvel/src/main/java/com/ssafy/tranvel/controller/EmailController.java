package com.ssafy.tranvel.controller;


import com.ssafy.tranvel.dto.EmailDto;
import com.ssafy.tranvel.service.EmailAuthService;
import jakarta.mail.MessagingException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
