package com.ssafy.tranvel.service;

import com.ssafy.tranvel.entity.User;
import com.ssafy.tranvel.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    // make return object of UserDetails from user and authority info
    // 수정 필요
    public UserDetails loadUserByUsername(final String id) {
        int userId;
        try {
            userId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new RuntimeException(id + " is not a valid user id");
        }

        return userRepository.findOneWithAuthoritiesById(userId)
                .map(user -> createUser(id, user))
                .orElseThrow(() -> new UsernameNotFoundException(id + " cannot found"));
    }

    private org.springframework.security.core.userdetails.User createUser(String id, User user) {
        if (!user.isActivated()) {
            throw new RuntimeException(id + " is not activated");
        }

//        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
//                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
//                .collect(Collectors.toList());
        // 권한 삭제로 인해 임시로 빈 권한 제공
        List<GrantedAuthority> grantedAuthorities = Collections.emptyList();

        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(),
                String.valueOf(user.getId()),
                user.getPassword(),
                grantedAuthorities);
    }
}
