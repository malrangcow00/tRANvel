package com.ssafy.tranvel.controller;


import com.ssafy.tranvel.dto.EmailDto;
import com.ssafy.tranvel.dto.ImagePostDto;
import com.ssafy.tranvel.dto.UserDto;
import com.ssafy.tranvel.dto.ResponseDto;
import com.ssafy.tranvel.entity.User;
import com.ssafy.tranvel.repository.EmailAuthDao;
import com.ssafy.tranvel.repository.NickNameDao;
import com.ssafy.tranvel.service.EmailAuthService;
import com.ssafy.tranvel.service.ImageUploadService;
import com.ssafy.tranvel.service.UserService;
import jakarta.mail.MessagingException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Getter @Setter
@RequiredArgsConstructor
@RestController
public class EmailController {

    private final EmailAuthService emailAuthService;
    private final UserService userService;
    private final EmailAuthDao emailAuthDao;
    private final NickNameDao nickNameDao;
    private final ImageUploadService imageUploadService;

    private ResponseDto response;

    @PostMapping("/email-auth")
    public ResponseEntity<ResponseDto> sendEmail(@RequestBody @Validated EmailDto emailDto) throws MessagingException, UnsupportedEncodingException {
        if (emailAuthService.emailDuplication(emailDto)) {
            response = new ResponseDto(false, "이미 회원가입 된 이메일입니다.", null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        emailAuthService.createEmailForm(emailDto.getEmail());
        response = new ResponseDto(true, "인증 코드를 전송하였습니다.", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     해당 controller 수정 예정
     emailAuthService.verifyEmail() 의 리턴값에 따른 sign up 권한 부여
     redis 에 저장 예정 중 입니다. 변경 가능성 있습니다.
     **/
    @PostMapping("/email-auth/verification")
    public ResponseEntity<ResponseDto> verifyCode(@RequestBody @Validated EmailDto emailDto) {
        if (!emailAuthService.verifyEmail(emailDto.getEmail(), emailDto.getVerificationCode())) {
            response = new ResponseDto(false, "인증 코드가 일치하지 않습니다.", null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        response = new ResponseDto(true, "이메일 인증에 성공하였습니다.", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

//    @PostMapping("/profileImage")
//    public ResponseEntity<ResponseDto> saveImage(ImagePostDto imagePostDto) throws IOException {
//        // image, email required
//
//        String profileimage;
//        profileimage = imageUploadService.uploadImage(imagePostDto, "profile");
//        response = new ResponseDto(true, "프로필 사진 s3 저장", profileimage);
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }


    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signUp(@RequestBody @Validated UserDto userDto) {

        if (!emailAuthDao.hasAuth(userDto.getEmail())) {
            response = new ResponseDto(false, "이메일 인증에 실패하였습니다.", null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        if (!userService.nickNameDuplicationCheck(userDto.getNickName(), userDto.getEmail())) {
            response = new ResponseDto(false, "중복된 닉네임입니다.", null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        userService.saveUserInfo(userDto);
        response = new ResponseDto(true, "회원가입에 성공하였습니다.", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
