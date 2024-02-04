package com.ssafy.tranvel.controller;

import com.ssafy.tranvel.dto.*;
import com.ssafy.tranvel.entity.Inquiry;
import com.ssafy.tranvel.entity.User;
import com.ssafy.tranvel.repository.EmailAuthDao;
import com.ssafy.tranvel.repository.InquiryRepository;
import com.ssafy.tranvel.repository.NickNameDao;
import com.ssafy.tranvel.repository.UserRepository;
import com.ssafy.tranvel.security.JwtAuthenticationFilter;
import com.ssafy.tranvel.service.EmailAuthService;
//import com.ssafy.tranvel.service.ImageUploadService;
import com.ssafy.tranvel.service.InquiryService;
import com.ssafy.tranvel.service.UserService;
import com.ssafy.tranvel.utility.JwtProvider;

import com.ssafy.tranvel.utility.SecurityUtility;
import jakarta.servlet.http.HttpServletRequest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
//    private final ImageUploadService imageUploadService;

    private ResponseDto response;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtProvider);
    }

//    @GetMapping("/auth/profile")
//    public ResponseEntity<UserDto> getUserProfile(HttpServletRequest request) {
//
//        Long userId = getUserId(request);
//
//        if (userId == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//
//        Optional<User> user = userRepository.findById(userId);
//
//        if (!user.isPresent()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//        UserDto userDto = UserDto.fromEntity(user.get());

//        String jwt = tokenProvider.createToken(authentication);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JwtAuthenticationFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

//        return ResponseEntity.ok(userDto);
//    }

//    @GetMapping("/auth/profile")
//    public ResponseEntity<UserDto> getUserProfile() {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//
//        Long userId = tokenProvider.getUserIdFromToken((String) authentication.getPrincipal());
//
//        Optional<User> user = userRepository.findById(userId);
//        if (!user.isPresent()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//        UserDto userDto = UserDto.fromEntity(user.get());
//        return ResponseEntity.ok(userDto);
//    }

    // 닉네임 유효성 검사
    @PostMapping("/duplication")
    public ResponseEntity<ResponseDto> nickNameCheck(@RequestBody @Validated
                                                     NickNameCheckDto nickNameCheckDto) {
        if (!userService.nickNameDuplicationCheck(nickNameCheckDto.getNickName(), nickNameCheckDto.getEmail())) {
            response = new ResponseDto(false, "이미 존재하는 닉네임 입니다.", nickNameCheckDto.getNickName());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        response = new ResponseDto(true, "사용 가능한 닉네임 입니다.", nickNameCheckDto.getNickName());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

//    // 프로필 이미지 등록
//    @PostMapping("/image")
//    public ResponseEntity<ResponseDto> saveImage(ProfileImageDto profileImageDto) throws IOException {
//        String profileimage = imageUploadService.uploadImage(profileImageDto.getProfileImage(), "profile", profileImageDto.getEmail());
//        response = new ResponseDto(true, "프로필 사진 s3 저장", profileimage);
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<ResponseDto> login(@RequestBody UserLoginDto userLoginDto) {
        TokenDto tokenDto = userService.login(userLoginDto.getEmail(), userLoginDto.getPassword());
        ResponseDto response = new ResponseDto(true, "로그인 성공", tokenDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // JWT 테스트
    @PostMapping("/test")
    public String test() {
        return "인증 성공";
    }

    // 사용자 프로필 조회
//    @GetMapping("/auth/profile")
//    public ResponseEntity<ResponseDto> getUserProfile() {
//        try {
//            String email = SecurityUtility.getCurrentUserId();
//            Optional<User> user = userRepository.findByEmail(email);
//
//            if (!user.isPresent()) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//            }
//
//            UserDto userDto = UserDto.fromEntity(user.get());
//            return ResponseEntity.ok(new ResponseDto(true, "사용자 프로필 정보", userDto));
//        } catch (Exception e) {
//            log.error("프로필 조회 중 오류 발생", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseDto(false, "서버 오류 발생", null));
//        }
//    }

    // 회원 문의글 작성
    @PostMapping("/auth/inquiry")
    public ResponseEntity<ResponseDto> postInquiry(@RequestBody @Validated InquiryDto inquiryDto) {
        Inquiry inquiry = inquiryService.createInquiry(inquiryDto);
        response = new ResponseDto(true, "문의 작성 완료", inquiry);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 회원 문의글 전체 조회
    @PostMapping("/auth/inquiry/list")
    public ResponseEntity<ResponseDto> getAllInquiries(@RequestBody @Validated InquiryDto inquiryDto) {
        List<Inquiry> inquiries = inquiryService.getAllInquiries(inquiryDto);
        response = new ResponseDto(true, "전체 문의 글 조회", inquiries);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // 회원 문의글 상세 조회
    @PostMapping("/auth/inquiry/detail")
    public ResponseEntity<ResponseDto> getInquiry(@RequestBody @Validated InquiryDto inquiryDto) {
        Inquiry inquiry = inquiryService.getInquiry(inquiryDto);
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
    public ResponseEntity<ResponseDto> deleteInquiry(@RequestBody @Validated InquiryDto inquiryDto) {
        inquiryService.deleteInquiry(inquiryDto);
        response = new ResponseDto(true, "문의 글이 삭제되었습니다.", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
