package com.ssafy.tranvel.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class EmailAuthDao {
    // redis 에 저장되는 Key 값이 중복되지 않도록 상수를 선언
    private final String CONST = "code:";
    // 이메일 인증 코드 유효시간 5분
    private final int LIMIT_TIME = 5 * 60;

    private final StringRedisTemplate stringRedisTemplate;

    public void createEmailAuthentication(String email, String verificationCode) {
        stringRedisTemplate.opsForValue()
                .set(CONST + email, verificationCode, Duration.ofSeconds(LIMIT_TIME));
    }

    public String getEmailAuthentication(String email) {
        return  stringRedisTemplate.opsForValue().get(CONST + email);
    }

    public void removeEmailAuthentication(String email) {
        stringRedisTemplate.delete(CONST + email);
    }

    public boolean hasKey(String email) {
        return stringRedisTemplate.hasKey(CONST + email);
    }

    public void giveAuthority(String email) {
        stringRedisTemplate.opsForValue()
                .set("Authority:" + email, "true", Duration.ofSeconds(LIMIT_TIME));
    }

    public boolean hasAuth(String email) {
        return stringRedisTemplate.opsForValue().get("Authority:" + email).equals("true");
    }
}
