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

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final InquiryRepository inquiryRepository;
    private final InquiryService inquiryService;

    private ResponseDto response;


//    public Long getUserIdFromToken() {
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey()
//                .build()
//                .parseClaimsJws()
//                .getBody();
//        return claims.get("userId", Long.class);
//    }

//    @GetMapping("/auth/{user-id}/profile")
//    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
//    public ResponseEntity<User> getMyUserInfo() {
//        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
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
