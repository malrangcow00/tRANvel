package com.ssafy.tranvel.controller;

import com.ssafy.tranvel.repository.EmailAuthDao;
import com.ssafy.tranvel.repository.NickNameDao;
import com.ssafy.tranvel.service.EmailAuthService;
import com.ssafy.tranvel.service.UserSignService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Getter
@Setter
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserSignService userSignService;
    private final EmailAuthDao emailAuthDao;
    private final NickNameDao nickNameDao;
    private final EmailAuthService emailAuthService;

    @GetMapping("/duplication/{nickname}")
    public ResponseEntity<String> nickNameCheck(@RequestBody @Validated
                                                @RequestParam("nickname") String nickName) {
//        if (!userSignService.nickNameDuplicationCheck(nickNameDto.getNickName(), nickNameDto.getEmail())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 닉네임 입니다.");
//        }
        if (!userSignService.nickNameDuplicationCheck(nickName, emailAuthService.accessEmail)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 닉네임 입니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("사용 가능한 닉네임 입니다.");
    }



}
