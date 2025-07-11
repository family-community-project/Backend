package com.example.family.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenResolver {
    private final RedisTemplate<String, String> redisTemplate;

    public Long getUserIdFromAccessToken(String token){
        String userId = redisTemplate.opsForValue().get("access_token:" + token);
        if(userId == null){
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
        return Long.parseLong(userId);
    }
}
