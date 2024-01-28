package com.ssafy.tranvel.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
//                .requestMatchers(
////                         임시 url
//                        new AntPathRequestMatcher("/signup", HttpMethod.POST),
//                        new AntPathRequestMatcher("/emailauth/", HttpMethod.POST),
//                        new AntPathRequestMatcher("/user/", HttpMethod.POST),
//                        new AntPathRequestMatcher("/user/", HttpMethod.GET),
//                        new AntPathRequestMatcher("/user/", HttpMethod.PUT),
//                        new AntPathRequestMatcher("/user/", HttpMethod.DELETE)).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
