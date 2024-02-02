package com.ssafy.tranvel.config;

import com.ssafy.tranvel.security.JwtAccessDeniedHandler;
import com.ssafy.tranvel.security.JwtAuthenticationEntryPoint;
import com.ssafy.tranvel.security.JwtFilter;
import com.ssafy.tranvel.utility.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
//import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
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
    public SecurityFilterChain customFilterChain(HttpSecurity httpSecurity) throws Exception {
        JwtFilter jwtFilter = new JwtFilter(tokenProvider);

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)

                .exceptionHandling(configurer -> configurer
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )

                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // api 수정 필요 (그 외 추가: 닉네임 중복 확인, 이메일 인증 요청/확인)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/signup").permitAll()
                        .requestMatchers("/email-auth/").permitAll()
                        .requestMatchers("/user/duplication/").permitAll()
                        .requestMatchers("/user/signin").permitAll()
//                        .anyRequest().authenticated()
                        .anyRequest().permitAll() // 개발 환경을 위해 임시로 모든 요청을 허용
                )

//                .addFilterBefore(new JwtFilter(tokenProvider), JwtFilter.class);
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


        return httpSecurity.build();
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(tokenProvider);
    }
}