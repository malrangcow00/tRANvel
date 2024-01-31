package com.ssafy.tranvel.controller;


import com.ssafy.tranvel.dto.EmailDto;
import com.ssafy.tranvel.dto.ResponseDto;
import com.ssafy.tranvel.dto.UserSignUpDto;
import com.ssafy.tranvel.repository.EmailAuthDao;
import com.ssafy.tranvel.repository.NickNameDao;
import com.ssafy.tranvel.service.EmailAuthService;
import com.ssafy.tranvel.service.UserSignupService;
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
    private final UserSignupService userSignupService;
    private final EmailAuthDao emailAuthDao;
    private final NickNameDao nickNameDao;

    private ResponseDto response;


    @PostMapping("/email-auth")
    public ResponseEntity<ResponseDto> sendEmail(@RequestBody @Validated EmailDto emailDto) throws MessagingException, UnsupportedEncodingException {
        emailAuthService.createEmailForm(emailDto.getEmail());
        response = new ResponseDto(true, "인증 코드를 전송하였습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


     /**
     해당 controller 수정 예정
     emailAuthService.verifyEmail() 의 리턴값에 따른 sign up 권한 부여
     redis 에 저장 예정 중 입니다. 변경 가능성 있습니다.
      **/
    @PostMapping("email-auth/verification")
    public ResponseEntity<ResponseDto> verifyCode(@RequestBody @Validated EmailDto emailDto) {
        if (!emailAuthService.verifyEmail(emailDto.getEmail(), emailDto.getVerificationCode())) {
            response = new ResponseDto(false, "인증 코드가 일치하지 않습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        response = new ResponseDto(true, "이메일 인증에 성공하였습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(response);



    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signUp(@RequestBody @Validated UserSignUpDto userSignUpDto) {

        if (!emailAuthDao.hasAuth(userSignUpDto.getEmail())) {
            response = new ResponseDto(false, "이메일 인증에 실패하였습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (!userSignupService.nickNameDuplicationCheck(userSignUpDto.getNickName(), userSignUpDto.getEmail())) {
            response = new ResponseDto(false, "중복된 닉네임입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        userSignupService.saveUserInfo(userSignUpDto);
        response = new ResponseDto(true, "회원가입에 성공하였습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
