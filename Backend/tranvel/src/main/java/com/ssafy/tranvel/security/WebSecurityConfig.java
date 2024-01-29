package com.ssafy.tranvel.security;

import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

//    @Autowired
//    private JwtRequestFilter jwtRequestFilter;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).cors(withDefaults())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/signup").permitAll()
                        .requestMatchers("/emailauth").permitAll()
                        .requestMatchers("/user").permitAll()
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .anyRequest().permitAll()
                )
                .formLogin(login -> login
                        .defaultSuccessUrl("/view/dashboard", true)
                        .permitAll()
                )
                .logout(withDefaults());
//                .authorizeHttpRequests(request -> request
////                .csrf().disable()
////                .authorizeRequests()
//                .requestMatchers(
////                         임시 url
//                        new AntPathRequestMatcher("/signup"),
//                        new AntPathRequestMatcher("/emailauth/"),
//                        new AntPathRequestMatcher("/user/"),
////                        new AntPathRequestMatcher("/user/"),
////                        new AntPathRequestMatcher("/user/"),
//                        new AntPathRequestMatcher("/user/").permitAll())
//                .anyRequest().authenticated()
//                .and()
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
