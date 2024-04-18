package com.lovememoir.server.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    //TODO type enum 만들 것
    public String generateAvatarKey(Long memberId, Long questionId) {
        return String.format("%s:%d:%d", "avatar", memberId, questionId);
    }

    public void save(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void saveWith7DaysExpiration(String key, String value) {
        redisTemplate.opsForValue().set(key, value, 7, TimeUnit.DAYS);
    }

    public String find(String key) {
        return redisTemplate.opsForValue().get(key);
    }


}
