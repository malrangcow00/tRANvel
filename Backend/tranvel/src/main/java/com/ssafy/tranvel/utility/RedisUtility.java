package com.ssafy.tranvel.utility;


import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class RedisUtility {

    private final StringRedisTemplate stringRedisTemplate;

    // CREATE
    public String getData(String key) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    // READ
    public boolean existData(String key) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
    }

    // UPDATE
    // try : duration type int, exception : duration type long
    public void setDataExpire(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        // 만료 시간
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    // DELETE
    public void deleteData(String key) {
        stringRedisTemplate.delete(key);
    }
}
