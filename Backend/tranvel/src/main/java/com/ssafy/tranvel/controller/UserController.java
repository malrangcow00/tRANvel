package com.ssafy.tranvel.controller;

import com.ssafy.tranvel.dto.InquiryDto;
import com.ssafy.tranvel.dto.ResponseDto;
import com.ssafy.tranvel.dto.UserDto;
import com.ssafy.tranvel.entity.Inquiry;
import com.ssafy.tranvel.entity.User;
import com.ssafy.tranvel.repository.EmailAuthDao;
import com.ssafy.tranvel.repository.InquiryRepository;
import com.ssafy.tranvel.repository.NickNameDao;
import com.ssafy.tranvel.repository.UserRepository;
import com.ssafy.tranvel.service.EmailAuthService;
import com.ssafy.tranvel.service.UserService;

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

    @GetMapping("/auth/{email}/profile")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<User> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

//    @GetMapping("/auth/{email}/profile")
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public ResponseEntity<User> getUserInfo(@PathVariable String email) {
//        return ResponseEntity.ok(userService.getUserWithAuthorities(email).get());
//    }

    private final InquiryRepository inquiryRepository;

    private ResponseDto response;

    @GetMapping("/duplication/{nickname}")
    public ResponseEntity<ResponseDto> nickNameCheck(@RequestBody @Validated
                                                     @RequestParam("nickname") String nickName) {
        if (!userService.nickNameDuplicationCheck(nickName, emailAuthService.accessEmail)) {
            response = new ResponseDto(false, "이미 존재하는 닉네임 입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        response = new ResponseDto(true, "사용 가능한 닉네임 입니다.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseDto> signinUser(@RequestBody @Validated UserDto userDto) {
        Optional<User> user = userRepository.findByEmail(userDto.getEmail());
        if (user.isEmpty()) {
            response = new ResponseDto(false, "회원 정보가 없습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (!user.get().getPassword().equals(userDto.getPassword())) {
            response = new ResponseDto(false, "비밀번호가 일치하지 않습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        response = new ResponseDto(true, "로그인 성공");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // 회원 문의글
    @PostMapping("/auth/{userid}/inquiry")
    public ResponseEntity<String> createInquiry(@RequestBody @Validated InquiryDto inquiryDto) {

        Inquiry inquiry = Inquiry.builder()
                .title(inquiryDto.getTitle())
                .content(inquiryDto.getContent())
                .build();

        inquiryRepository.save(inquiry);

        return ResponseEntity.status(HttpStatus.CREATED).body("게시글 작성 완료");
    }

    // 나중에 토큰으로 바꿔주세요
    // 바꿀 때 {userid} 삭제
    @GetMapping("/auth/{userid}/inquiry")
    public ResponseEntity<List<Inquiry>> searchAllInquiries(@RequestBody @Validated @PathVariable("userid") int userId) {
        // Optional<Inquiry> inquiries = inquiryRepository.findByUser_Id(userId);
        Optional<User> user = userRepository.findById(userId);
        // get 대신 orElse 수정
        List<Inquiry> inquiries = user.get().getInquiryList();

        return ResponseEntity.status(HttpStatus.OK).body(inquiries);
    }

    // 여기서부터 수정

//    @GetMapping("/auth/{userid}/inquiry/{inquiry-id}")
//    public ResponseEntity<Optional<Inquiry>> searchInquiry(@RequestBody @Validated @PathVariable("userid") int userId,
//        @PathVariable("inquiry-id") int inquiryId) {
//        Optional<User> user = userRepository.findById(userId);
//        List<Inquiry> inquiries = user.get().getInquiryList();
//
//
//        return ResponseEntity.status(HttpStatus.OK).body();
//    }
}
