//package com.ssafy.tranvel.security;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import com.ssafy.tranvel.util.JwtUtil;
//
//import java.io.IOException;
//
//
//public class JwtRequestFilter extends OncePerRequestFilter {
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//        final String authorizationHeader = request.getHeader("Authorization"); // Authorization 헤더에서 토큰을 가져오기
//
//        String email = null;
//        String jwt = null;
//
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            jwt = authorizationHeader.substring(7); // 공백 포함 "Bearer " 제외
//
//            try {
//                email = jwtUtil.getUsernameFromToken(jwt);
//            } catch (IllegalArgumentException e) {
//                System.out.println("권한이 없습니다.");
//            } catch (ExpiredJwtException e) {
//                System.out.println("세션이 만료 되었습니다.");
//            }
//        } else {
//            logger.warn("JWT Token does not begin with Bearer String");
//        }
//
//        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(email);
//
//            if (jwtUtil.validateToken(jwt, userDetails)) {
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities()
//                );
//
//                usernamePasswordAuthenticationToken
//                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            }
//        }
//
//        chain.doFilter(request, response);
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//    }
//}
