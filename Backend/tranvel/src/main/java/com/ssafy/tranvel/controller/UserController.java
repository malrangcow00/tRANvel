package com.ssafy.tranvel.controller;

import com.ssafy.tranvel.dto.*;
import com.ssafy.tranvel.entity.Inquiry;
import com.ssafy.tranvel.entity.User;
import com.ssafy.tranvel.repository.EmailAuthDao;
import com.ssafy.tranvel.repository.InquiryRepository;
import com.ssafy.tranvel.repository.NickNameDao;
import com.ssafy.tranvel.repository.UserRepository;
//import com.ssafy.tranvel.security.JwtAuthenticationFilter;
import com.ssafy.tranvel.service.EmailAuthService;
import com.ssafy.tranvel.service.ImageUploadService;
import com.ssafy.tranvel.service.InquiryService;
import com.ssafy.tranvel.service.UserService;
import com.ssafy.tranvel.utility.JwtProvider;

import com.ssafy.tranvel.utility.SecurityUtility;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@Getter @Setter
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final EmailAuthDao emailAuthDao;
    private final NickNameDao nickNameDao;
    private final EmailAuthService emailAuthService;
    private final JwtProvider jwtProvider;
    private final InquiryRepository inquiryRepository;
    private final InquiryService inquiryService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final ImageUploadService imageUploadService;

    private ResponseDto response;

    // 닉네임 유효성 검사
    @PostMapping("/duplication")
    public ResponseEntity<ResponseDto> nickNameCheck(String email, String nickName) {
        if (!userService.nickNameDuplicationCheck(nickName, email)) {
            response = new ResponseDto(false, "이미 존재하는 닉네임 입니다.", null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        response = new ResponseDto(true, "사용 가능한 닉네임 입니다.", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 프로필 이미지 등록
    @PostMapping(value = "/profileImage", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> saveProfileImage(@RequestPart(value = "image",required = true) MultipartFile image) throws IOException {

        imageUploadService.uploadProfileImage(image);
        response = new ResponseDto(true, "프로필 사진 s3 저장", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 로그인
    @PostMapping("/signin")

    public ResponseEntity<ResponseDto> login(@RequestBody UserLoginDto userLoginDto) {
        TokenDto tokenDto = userService.login(userLoginDto.getEmail(), userLoginDto.getPassword());
        ResponseDto response = new ResponseDto(true, "로그인 성공", tokenDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 사용자 프로필 조회
    @GetMapping("/auth/profile")
    public ResponseEntity<ResponseDto> getProfile() {
        try {
            // 현재 인증된 사용자의 ID(이메일) 조회

            UserProfileDto userProfileDto = userService.getProfile();

            if (userProfileDto == null) {
                response = new ResponseDto(false, "사용자를 찾을 수 없습니다.", null);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            response = new ResponseDto(true, "사용자 정보 조회 성공", userProfileDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response = new ResponseDto(false, "오류 발생: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PutMapping(value ="/auth/profile", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> updateProfile(@RequestPart UserUpdateDto userUpdateDto, @RequestPart(value = "image",required = false) MultipartFile image) throws IOException {

        boolean check = userService.updateProfile(userUpdateDto, image);
            if (!check) {
                response = new ResponseDto(false, "비밀번호 확인 불일치", null);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            response = new ResponseDto(true, "사용자 정보 수정 성공", null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }


    // 회원 문의글 작성
    @PostMapping("/auth/inquiry")
    public ResponseEntity<ResponseDto> postInquiry(String title, String content) {
        Inquiry inquiry = inquiryService.createInquiry(title, content);
        response = new ResponseDto(true, "문의 작성 완료", inquiry);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 회원 문의글 전체 조회
    @GetMapping("/auth/inquiry/list")
    public ResponseEntity<ResponseDto> getAllInquiries() {
        List<Inquiry> inquiries = inquiryService.getAllInquiries();
        response = new ResponseDto(true, "전체 문의 글 조회", inquiries);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 회원 문의글 상세 조회
    @PostMapping("/auth/inquiry/detail")
    public ResponseEntity<ResponseDto> getInquiry(Long inquiryId) {
        Inquiry inquiry = inquiryService.getInquiry(inquiryId);
        response = new ResponseDto(true, "상세 문의 글 조회", inquiry);
        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 회원 문의글 수정
    @PutMapping("/auth/inquiry/detail")
    public ResponseEntity<ResponseDto> putInquiry(@RequestBody @Validated InquiryDto inquiryDto) {
        Inquiry inquiry = inquiryService.updateInquiry(inquiryDto);
        response = new ResponseDto(true, "상세 문의 글 수정", inquiry);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 회원 문의글 삭제
    @DeleteMapping("/auth/inquiry/detail")
    public ResponseEntity<ResponseDto> deleteInquiry(Long inquiryId) {
        inquiryService.deleteInquiry(inquiryId);
        response = new ResponseDto(true, "문의 글이 삭제되었습니다.", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
