package com.ssafy.tranvel.service;

import com.ssafy.tranvel.entity.User;
import com.ssafy.tranvel.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
    /**
     * 여기서 PasswordEncoder를 통해 UserDetails 객체를 생성할 때 encoding을 해줌 => 왜냐하면 Spring Security는 사용자 검증을 위해 encoding된 password와 그렇지 않은 password를 비교하기 때문
     * 실제로는 DB 자체에 encoding된 password 값을 갖고 있고 그냥 memer.getPassword()로 encoding된 password를 꺼내는 것이 좋지만, 예제에서는 편의를 위해 검증 객체를 생성할 때 encoding을 해줌.
     */
    private UserDetails createUserDetails(User user) {
        return User.builder()
                .email(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
//                .roles(user.getRoles().toArray(new String[0])) // 권한 정보 아직 없음
                .build();
    }
}

//    private final UserRepository userRepository;
//
//    @Override
//    @Transactional
//    // make return object of UserDetails from user and authority info (권한 정보 제거됨)
//    // 수정 필요
//    public UserDetails loadUserByUsername(final String id) {
//        int userId;
//        try {
//            userId = Integer.parseInt(id);
//        } catch (NumberFormatException e) {
//            throw new RuntimeException(id + " is not a valid user id");
//        }
//
//        return userRepository.findById(userId)
//                .map(user -> createUser(id, user))
//                .orElseThrow(() -> new UsernameNotFoundException(id + " cannot found"));
//    }
//
//    private org.springframework.security.core.userdetails.User createUser(String id, User user) {
//        if (!user.isActivated()) {
//            throw new RuntimeException(id + " is not activated");
//        }
//
//        List<GrantedAuthority> grantedAuthorities = Collections.emptyList();
//
//        return new org.springframework.security.core.userdetails.User(
//                String.valueOf(user.getId()),
//                user.getPassword(),
//                grantedAuthorities);
//    }
//}
