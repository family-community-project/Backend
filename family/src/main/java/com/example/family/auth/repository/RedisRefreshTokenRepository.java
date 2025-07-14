package com.example.family.auth.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisRefreshTokenRepository implements RefreshTokenRepository {

    private final StringRedisTemplate redisTemplate;
    private static final String REFRESH_TOKEN_PREFIX = "refresh:";

    @Override
    public void saveRefreshToken(String userId, String refreshToken, long expiration) {
        redisTemplate.opsForValue().set(
                REFRESH_TOKEN_PREFIX + userId,
                refreshToken,
                expiration,
                TimeUnit.MILLISECONDS
        );
    }

    @Override
    public String getRefreshToken(String userId) {
        return redisTemplate.opsForValue().get(REFRESH_TOKEN_PREFIX + userId);
    }

    @Override
    public void deleteRefreshToken(String userId) {
        redisTemplate.delete(REFRESH_TOKEN_PREFIX + userId);
    }
}
