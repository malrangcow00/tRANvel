package com.ssafy.tranvel.service;

import com.ssafy.tranvel.dto.*;
import com.ssafy.tranvel.entity.User;
//import com.ssafy.tranvel.utility.SecurityUtility;
import com.ssafy.tranvel.repository.NickNameDao;
import com.ssafy.tranvel.repository.UserRepository;

import com.ssafy.tranvel.utility.JwtProvider;
import com.ssafy.tranvel.utility.SecurityUtility;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Getter
@Setter
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final NickNameDao nickNameDao;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder PasswordEncoder;
    private  final ImageUploadService imageUploadService;

    public void createNickNameInRedis(String nickName, String email) {
        nickNameDao.nickNameStash(nickName, email);
    }

    // 회원 정보에서도 조회하여 중복확인 필요
    // 현재는 Redis 에서 회원가입 시 동시 확인 중복체크만 적용
    // 회원가입 시 Redis 의 nickName key 삭제
    public boolean nickNameDuplicationCheck(String nickName, String email) {
        if (userRepository.findByNickName(nickName).isPresent()) {
            // "이미 존재하는 닉네임 입니다."
            return false;
        }
        if (nickNameDao.hasKey(nickName) &&
                !nickNameDao.getStashedNickName(nickName)
                        .equals(email)) {
            // "이미 존재하는 닉네임 입니다."
            return false;
        }
        // 인증 코드 유효성 통과 시, 해당 인증 코드가 사라지기 때문에 재접근 시 새로운 코드 발급 필요
        createNickNameInRedis(nickName, email);
        // "사용 가능한 닉네임 입니다."
        return true;
    }

//    private boolean isVerify(String nickName, String email) {
//        return !(nickNameDao.hasKey(nickName) &&
//                nickNameDao.getStashedNickName(nickName)
//                        .equals(email));
//    }

    public void saveUserInfo(UserDto userDto) {
//        if (nickNameDao.getStashedNickName(nickName).equals(email)) {

        User user = User.builder()
                .email(userDto.getEmail())
                .nickName(userDto.getNickName())
                // 암호화 필요
                .password(userDto.getPassword())
//                .password(PasswordEncoder.encode(userDto.getPassword()))
//                .profileImage((userDto.getProfileImage() == null) ? userDto.getProfileImage() : null)
                .balance(0)
                .build();

        userRepository.save(user);
        }

    @Transactional
    public TokenDto login(String memberId, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = jwtProvider.generateTokens(authentication);

        return tokenDto;
    }


    public UserProfileDto getProfile() {
        String userId = SecurityUtility.getCurrentUserId();
        Optional<User> userOptional = userRepository.findByEmail(userId);
        if (!userOptional.isPresent()) {
            return null;
        }
        User user = userOptional.get();
        UserProfileDto userProfileDto = UserProfileDto.builder()
                .id(user.getId())
                .email(userId)
                .nickName(user.getNickName())
                .profileImage(user.getProfileImage())
                .balance(user.getBalance())
                .build();

        return userProfileDto;
    }




    public boolean updateProfile(UserUpdateDto userUpdateDto, MultipartFile image) throws IOException {
        String userId = SecurityUtility.getCurrentUserId(); // 이메일
        User user = userRepository.findByEmail(userId).get();
        String profileImage = null;
        if (userUpdateDto.getPassword1() != null && userUpdateDto.getPassword2() != null) {
            if (userUpdateDto.getPassword1().equals(userUpdateDto.getPassword2())) {
                if (image != null) {
                    profileImage = imageUploadService.uploadProfileImage(image);
                }
                System.out.println(userUpdateDto.getPassword1());
                User updateUser = User.builder()
                        .id(user.getId())
                        .email(userId)
                        .password(user.getPassword())
                        .nickName(userUpdateDto.getNickName() == null?user.getNickName():userUpdateDto.getNickName())
                        .profileImage(profileImage == null?user.getProfileImage():profileImage)
                        .balance(user.getBalance())
                        .inquiryList(user.getInquiryList())
                        .roles(user.getRoles())
                        .roomHistories(user.getRoomHistories())
                        .build();
                userRepository.save(updateUser);
            } else {
                return false;
            }
            return true;
        } else {
            if (image != null) {
                profileImage = imageUploadService.uploadProfileImage(image);
            }
            System.out.println(userUpdateDto.getPassword1());
            User updateUser = User.builder()
                    .id(user.getId())
                    .email(userId)
                    .password(userUpdateDto.getPassword1())
                    .nickName(userUpdateDto.getNickName() == null?user.getNickName():userUpdateDto.getNickName())
                    .profileImage(profileImage == null?user.getProfileImage():profileImage)
                    .balance(user.getBalance())
                    .inquiryList(user.getInquiryList())
                    .roles(user.getRoles())
                    .roomHistories(user.getRoomHistories())
                    .build();
            userRepository.save(updateUser);
            return true;
        }


    }
}