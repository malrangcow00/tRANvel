package com.ssafy.tranvel.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;


@Repository
@RequiredArgsConstructor
public class NickNameDao {
    // redis 에 저장되는 Key 값이 중복되지 않도록 상수를 선언
    private final String CONST = "nickName:";
    // 이메일 인증 코드 유효시간 10분
    private final int LIMIT_TIME = 10 * 60;

    private final StringRedisTemplate stringRedisTemplate;

    public void nickNameStash(String nickName, String email) {
        stringRedisTemplate.opsForValue()
                .set(CONST + nickName, email, Duration.ofSeconds(LIMIT_TIME));
    }

    public String getStashedNickName(String nickName) {
        return  stringRedisTemplate.opsForValue().get(CONST + nickName);
    }

    public void removeStashedNickName(String nickName) {
        stringRedisTemplate.delete(CONST + nickName);
    }

    public boolean hasKey(String nickName) {
        return stringRedisTemplate.hasKey(CONST + nickName);
    }
}
