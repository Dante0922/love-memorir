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
    private String getKey(String type, String key) {
        return type + ":" + key;
    }

    public void save(String type, String key, String value) {
        redisTemplate.opsForValue().set(getKey(type, key), value);
    }
    public void saveWith7DaysExpiration(String type, String key, String value) {
        redisTemplate.opsForValue().set(getKey(type, key), value, 7, TimeUnit.DAYS);
    }

    public String find(String type, String key) {
        return redisTemplate.opsForValue().get(getKey(type, key));
    }
}
