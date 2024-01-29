package com.ssafy.tranvel.controller;

import com.ssafy.tranvel.dto.InquiryDto;
import com.ssafy.tranvel.dto.UserSignInDto;
import com.ssafy.tranvel.entity.Inquiry;
import com.ssafy.tranvel.entity.User;
import com.ssafy.tranvel.repository.EmailAuthDao;
import com.ssafy.tranvel.repository.InquiryRepository;
import com.ssafy.tranvel.repository.NickNameDao;
import com.ssafy.tranvel.repository.UserRepository;
import com.ssafy.tranvel.service.EmailAuthService;
import com.ssafy.tranvel.service.UserSignupService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Getter
@Setter
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserSignupService userSignupService;
    private final UserRepository userRepository;
    private final EmailAuthDao emailAuthDao;
    private final NickNameDao nickNameDao;
    private final EmailAuthService emailAuthService;

    private final InquiryRepository inquiryRepository;

    @GetMapping("/duplication/{nickname}")
    public ResponseEntity<String> nickNameCheck(@RequestBody @Validated
                                                @RequestParam("nickname") String nickName) {
        if (!userSignupService.nickNameDuplicationCheck(nickName, emailAuthService.accessEmail)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 닉네임 입니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("사용 가능한 닉네임 입니다.");
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signinUser(@RequestBody @Validated UserSignInDto userSignInDto) {
        Optional<User> user = userRepository.findByEmail(userSignInDto.getEmail());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 정보가 없습니다.");
        }
        if (!user.get().getPassword().equals(userSignInDto.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호가 일치하지 않습니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("로그인 성공");
    }


    // 회원 문의글
    @PostMapping("/inquiry")
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
    @GetMapping("/inquiry/{userid}")
    public ResponseEntity<List<Inquiry>> searchAllInquiries(@RequestBody @Validated @PathVariable("userid") int userId) {
        // Optional<Inquiry> inquiries = inquiryRepository.findByUser_Id(userId);
        Optional<User> user = userRepository.findById(userId);
        // get 대신 orElse 수정
        List<Inquiry> inquiries = user.get().getInquiryList();

        return ResponseEntity.status(HttpStatus.OK).body(inquiries);
    }

    // 여기서부터 수정

//    @GetMapping("/inquiry/{userid}/{inquiry-id}")
//    public ResponseEntity<Optional<Inquiry>> searchInquiry(@RequestBody @Validated @PathVariable("userid") int userId,
//        @PathVariable("inquiry-id") int inquiryId) {
//        Optional<User> user = userRepository.findById(userId);
//        List<Inquiry> inquiries = user.get().getInquiryList();
//
//
//        return ResponseEntity.status(HttpStatus.OK).body();
//    }


}
