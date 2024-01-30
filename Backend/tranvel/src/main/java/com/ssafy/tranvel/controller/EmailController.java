package com.ssafy.tranvel.controller;


import com.ssafy.tranvel.dto.EmailDto;
import com.ssafy.tranvel.dto.UserDto;
import com.ssafy.tranvel.repository.EmailAuthDao;
import com.ssafy.tranvel.repository.NickNameDao;
import com.ssafy.tranvel.service.EmailAuthService;
import com.ssafy.tranvel.service.UserService;
import jakarta.mail.MessagingException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Getter @Setter
@RequiredArgsConstructor
@RestController
public class EmailController {

    private final EmailAuthService emailAuthService;
    private final UserService userService;
    private final EmailAuthDao emailAuthDao;
    private final NickNameDao nickNameDao;


    @PostMapping("/emailauth")
    public void sendEmail(@RequestBody @Validated EmailDto emailDto) throws MessagingException, UnsupportedEncodingException {
        emailAuthService.createEmailForm(emailDto.getEmail());
    }


     /**
     해당 controller 수정 예정
     emailAuthService.verifyEmail() 의 리턴값에 따른 sign up 권한 부여
     redis 에 저장 예정 중 입니다. 변경 가능성 있습니다.
      **/
    @PostMapping("emailauth/verification")
    public String verifyCode(@RequestBody @Validated EmailDto emailDto) {
        return emailAuthService.verifyEmail(emailDto.getEmail(), emailDto.getVerificationCode());

    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody @Validated UserDto userDto) {
        if (!emailAuthDao.hasAuth(userDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이메일 인증에 실패하였습니다.");
        }
        if (!userService.nickNameDuplicationCheck(userDto.getNickName(), userDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("중복된 닉네임입니다.");
        }
        userService.saveUserInfo(userDto);
        return ResponseEntity.status(HttpStatus.OK).body("회원가입에 성공하였습니다.");
    }
}
