package com.ssafy.tranvel.config;

import com.ssafy.tranvel.security.JwtAccessDeniedHandler;
import com.ssafy.tranvel.security.JwtAuthenticationEntryPoint;
import com.ssafy.tranvel.security.JwtFilter;
import com.ssafy.tranvel.util.TokenProvider;
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

    @Bean(name = "customSecurityFilterChain")
    public SecurityFilterChain customFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
//                .csrf().disable()
                .csrf(AbstractHttpConfigurer::disable)

//                .exceptionHandling()
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                .accessDeniedHandler(jwtAccessDeniedHandler)
                .exceptionHandling(configurer -> configurer
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
//                .and()
//                .authorizeRequests()
                // api 수정 필요 (그 외 추가: 닉네임 중복 확인, 이메일 인증 요청/확인)
//                .antMatchers("/signup", "/email-auth/", "/user/duplication/", "/user/signin").permitAll()
//                .requestMatchers("/signup").permitAll()
//                .requestMatchers("/email-auth/").permitAll()
//                .requestMatchers("/user/duplication/").permitAll()
//                .requestMatchers("/user/signin").permitAll()
//                .anyRequest().authenticated()
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
//                        .antMatchers("/signup", "/email-auth/", "/user/duplication/", "/user/signin").permitAll()
                        .requestMatchers("/signup").permitAll()
                        .requestMatchers("/email-auth/").permitAll()
                        .requestMatchers("/user/duplication/").permitAll()
                        .requestMatchers("/user/signin").permitAll()
//                        .anyRequest().authenticated()
                        .anyRequest().permitAll() // 개발 환경을 위해 임시로 모든 요청을 허용
                )

//                .apply(new JwtSecurityConfig(tokenProvider))
                .addFilterBefore(new JwtFilter(tokenProvider), JwtFilter.class);

        return httpSecurity.build();
    }


}