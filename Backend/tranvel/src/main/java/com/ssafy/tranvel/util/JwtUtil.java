//package com.ssafy.tranvel.util;
//
//public class JwtUtil {
//    private String securityKey = "ax2+3y4z5w6v7u8t9s0r1q2p3o4n5m6l7k8j9i0h1g2f3e4d5c6b7a8";
//
//    public String generateToken(String email) {
//        return Jwts.builder()
//                .setSubject(email)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 개발 환경을 위해 1시간으로 설정 > 추후 협의를 통해 시간 조정하기
//                .signWith(SignatureAlgorithm.HS256, securityKey)
//                .compact();
//    }
//
//    public Claims validateToken(String token) {
//        return Jwts.parser().setSigningKey(securityKey).parseClaimsJws(token).getBody();
//    }
//}
