package com.ssafy.tranvel.controller;

import com.ssafy.tranvel.dto.ResponseDto;
import com.ssafy.tranvel.dto.UserSignInDto;
import com.ssafy.tranvel.entity.User;
import com.ssafy.tranvel.repository.EmailAuthDao;
import com.ssafy.tranvel.repository.NickNameDao;
import com.ssafy.tranvel.repository.UserRepository;
import com.ssafy.tranvel.service.EmailAuthService;
import com.ssafy.tranvel.service.UserSignService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Getter
@Setter
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserSignService userSignService;
    private final UserRepository userRepository;
    private final EmailAuthDao emailAuthDao;
    private final NickNameDao nickNameDao;
    private final EmailAuthService emailAuthService;

    @GetMapping("/duplication/{nickname}")
    public ResponseEntity<ResponseDto> nickNameCheck(@RequestBody @Validated
                                                     @RequestParam("nickname") String nickName) {
        if (!userSignService.nickNameDuplicationCheck(nickName, emailAuthService.accessEmail)) {
            ResponseDto response = new ResponseDto(false, "이미 존재하는 닉네임 입니다.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        ResponseDto response = new ResponseDto(false, "사용 가능한 닉네임 입니다.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseDto> signinUser(@RequestBody @Validated UserSignInDto userSignInDto) {
        Optional<User> user = userRepository.findByEmail(userSignInDto.getEmail());
        if (user.isEmpty()) {
            ResponseDto response = new ResponseDto(false, "회원 정보가 없습니다.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        if (!user.get().getPassword().equals(userSignInDto.getPassword())) {
            ResponseDto response = new ResponseDto(false, "비밀번호가 일치하지 않습니다.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        ResponseDto response = new ResponseDto(false, "로그인 성공");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
