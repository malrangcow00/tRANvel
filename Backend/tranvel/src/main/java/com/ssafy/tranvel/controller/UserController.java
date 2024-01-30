package com.ssafy.tranvel.controller;

import com.ssafy.tranvel.entity.User;
import com.ssafy.tranvel.repository.EmailAuthDao;
import com.ssafy.tranvel.repository.NickNameDao;
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

@RestController
@Getter
@Setter
@RequiredArgsConstructor
@RequestMapping("/") // API 수정 필요
public class UserController {

    private final UserService userService;
    private final EmailAuthDao emailAuthDao;
    private final NickNameDao nickNameDao;
    private final EmailAuthService emailAuthService;

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<User> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

    @GetMapping("/user/{email}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<User> getUserInfo(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(email).get());
    }

    @GetMapping("/duplication/{nickname}")
    public ResponseEntity<String> nickNameCheck(@RequestBody @Validated
                                                @RequestParam("nickname") String nickName) {
//        if (!userSignService.nickNameDuplicationCheck(nickNameDto.getNickName(), nickNameDto.getEmail())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 닉네임 입니다.");
//        }
        if (!userService.nickNameDuplicationCheck(nickName, emailAuthService.accessEmail)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 닉네임 입니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("사용 가능한 닉네임 입니다.");
    }



}
