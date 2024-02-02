package com.ssafy.tranvel.controller;

import com.ssafy.tranvel.dto.*;
import com.ssafy.tranvel.entity.Inquiry;
import com.ssafy.tranvel.entity.User;
import com.ssafy.tranvel.repository.EmailAuthDao;
import com.ssafy.tranvel.repository.InquiryRepository;
import com.ssafy.tranvel.repository.NickNameDao;
import com.ssafy.tranvel.repository.UserRepository;
import com.ssafy.tranvel.service.EmailAuthService;
import com.ssafy.tranvel.service.InquiryService;
import com.ssafy.tranvel.service.UserService;
import com.ssafy.tranvel.security.JwtFilter;
import com.ssafy.tranvel.utility.TokenProvider;

import jakarta.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Getter
@Setter
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final EmailAuthDao emailAuthDao;
    private final NickNameDao nickNameDao;
    private final EmailAuthService emailAuthService;
    private final TokenProvider tokenProvider;
    private final JwtFilter jwtFilter;
    private final InquiryRepository inquiryRepository;
    private final InquiryService inquiryService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private ResponseDto response;

    // 헤더의 토큰에서 ID 가져오기
    public Long getUserId(HttpServletRequest request) {
        String jwt = jwtFilter.resolveToken(request);
        return tokenProvider.getUserIdFromToken(jwt);
    }

    @GetMapping("/auth/profile")
//    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserDto> getUserProfile(HttpServletRequest request) {

        Long userId = getUserId(request);

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        UserDto userDto = UserDto.fromEntity(user.get());

//        String jwt = tokenProvider.createToken(authentication);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return ResponseEntity.ok(userDto);
    }

//    @GetMapping("/auth/profile")
//    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
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

    @PostMapping("/duplication")
    public ResponseEntity<ResponseDto> nickNameCheck(@RequestBody @Validated
                                                     NickNameCheckDto nickNameCheckDto) {
        if (!userService.nickNameDuplicationCheck(nickNameCheckDto.getNickName(), nickNameCheckDto.getEmail())) {
            response = new ResponseDto(false, "이미 존재하는 닉네임 입니다.", null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        response = new ResponseDto(true, "사용 가능한 닉네임 입니다.", nickNameCheckDto.getNickName());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

//    @PostMapping("/signin")
//    public ResponseEntity<ResponseDto> signinUser(@RequestBody @Validated LoginDto loginDto) {
//        Optional<User> user = userRepository.findByEmail(loginDto.getEmail());
//        if (user.isEmpty()) {
//            response = new ResponseDto(false, "회원 정보가 없습니다.", null);
//            return ResponseEntity.status(HttpStatus.OK).body(response);
//        }
//        if (!user.get().getPassword().equals(loginDto.getPassword())) {
//            response = new ResponseDto(false, "비밀번호가 일치하지 않습니다.", null);
//            return ResponseEntity.status(HttpStatus.OK).body(response);
//        }
//
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
//
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String jwt = tokenProvider.createToken(authentication);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
//
//        response = new ResponseDto(true, "로그인 성공", user.get().getId());
//        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(response);
//    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseDto> signinUser(@RequestBody @Validated LoginDto loginDto) {
        Optional<User> user = userRepository.findByEmail(loginDto.getEmail());
        if (user.isEmpty()) {
            response = new ResponseDto(false, "회원 정보가 없습니다.", null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        if (!user.get().getPassword().equals(loginDto.getPassword())) {
            response = new ResponseDto(false, "비밀번호가 일치하지 않습니다.", null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        response = new ResponseDto(true, "로그인 성공", user.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 회원 문의글
    @PostMapping("/auth/inquiry")
    public ResponseEntity<ResponseDto> postInquiry(@RequestBody @Validated InquiryDto inquiryDto) {
        Inquiry inquiry = inquiryService.createInquiry(inquiryDto);
        response = new ResponseDto(true, "문의 작성 완료", inquiry);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/auth/inquiry/list")
    public ResponseEntity<ResponseDto> getAllInquiries(@RequestBody @Validated InquiryDto inquiryDto) {
        List<Inquiry> inquiries = inquiryService.getAllInquiries(inquiryDto);
        response = new ResponseDto(true, "전체 문의 글 조회", inquiries);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/auth/inquiry/detail")
    public ResponseEntity<ResponseDto> getInquiry(@RequestBody @Validated InquiryDto inquiryDto) {
        Inquiry inquiry = inquiryService.getInquiry(inquiryDto);
        response = new ResponseDto(true, "상세 문의 글 조회", inquiry);
        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PutMapping("/auth/inquiry/detail")
    public ResponseEntity<ResponseDto> putInquiry(@RequestBody @Validated InquiryDto inquiryDto) {
        Inquiry inquiry = inquiryService.updateInquiry(inquiryDto);
        response = new ResponseDto(true, "상세 문의 글 수정", inquiry);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @DeleteMapping("/auth/inquiry/detail")
    public ResponseEntity<ResponseDto> deleteInquiry(@RequestBody @Validated InquiryDto inquiryDto) {
        inquiryService.deleteInquiry(inquiryDto);
        response = new ResponseDto(true, "문의 글이 삭제되었습니다.", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
