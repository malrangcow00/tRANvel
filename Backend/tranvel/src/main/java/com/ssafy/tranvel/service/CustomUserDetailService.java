package com.ssafy.tranvel.service;

import com.ssafy.tranvel.entity.User;
import com.ssafy.tranvel.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    // make return object of UserDetails from user and authority info (권한 정보 제거됨)
    // 수정 필요
    public UserDetails loadUserByUsername(final String id) {
        int userId;
        try {
            userId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new RuntimeException(id + " is not a valid user id");
        }

        return userRepository.findById(userId)
                .map(user -> createUser(id, user))
                .orElseThrow(() -> new UsernameNotFoundException(id + " cannot found"));
    }

    private org.springframework.security.core.userdetails.User createUser(String id, User user) {
        if (!user.isActivated()) {
            throw new RuntimeException(id + " is not activated");
        }

        List<GrantedAuthority> grantedAuthorities = Collections.emptyList();

        return new org.springframework.security.core.userdetails.User(
                String.valueOf(user.getId()),
                user.getPassword(),
                grantedAuthorities);
    }
}
