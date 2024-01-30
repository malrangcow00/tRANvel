package com.ssafy.tranvel.config;

import com.ssafy.tranvel.security.JwtAccessDeniedHandler;
import com.ssafy.tranvel.security.JwtAuthenticationEntryPoint;
import com.ssafy.tranvel.security.JwtFilter;
import com.ssafy.tranvel.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configurable
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                // api 수정 필요 (그 외 추가: 닉네임 중복 확인, 이메일 인증 요청/확인)
                .requestMatchers("/signup").permitAll()
                .requestMatchers("/email-auth/").permitAll()
                .requestMatchers("/user/duplication/").permitAll()
                .requestMatchers("/user/signin").permitAll()
                .anyRequest().authenticated()

                .and()
//                .apply(new JwtSecurityConfig(tokenProvider)) //
                .addFilterBefore(new JwtFilter(tokenProvider), JwtFilter.class);

        return httpSecurity.build();
    }


}