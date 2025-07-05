package com.example.family.invite.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RedisInviteRepository implements InviteRepository {
    private final StringRedisTemplate redisTemplate;
    private static final String PREFIX = "invite:";
    private static final Duration EXPIRE_DURATION = Duration.ofDays(1);

    @Override
    public void saveInviteCode(String code, Long familyId, int maxUses) {
        redisTemplate.opsForHash().put(PREFIX + code, "familyId", String.valueOf(familyId));
        redisTemplate.opsForHash().put(PREFIX + code, "remaining", String.valueOf(maxUses));
        redisTemplate.expire(PREFIX + code, EXPIRE_DURATION);

        redisTemplate.opsForValue().set(PREFIX + "family:" + familyId, code, EXPIRE_DURATION);
    }

    public Optional<String> findExistingCodeByFamilyId(Long familyId) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(PREFIX + "family:" + familyId));
    }

    @Override
    public String findFamilyIdByCode(String code) {
        return (String) redisTemplate.opsForHash().get(PREFIX + code, "familyId");
    }

    @Override
    public Integer findRemainingUsesByCode(String code) {
        String value = (String) redisTemplate.opsForHash().get(PREFIX + code, "remaining");
        return value == null ? null : Integer.parseInt(value);
    }

    @Override
    public void decreaseRemainingUses(String code) {
        String value = (String) redisTemplate.opsForHash().get(PREFIX + code, "remaining");
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("remaining 값이 정수가 아닙니다: " + value);
        }

        redisTemplate.opsForHash().increment(PREFIX + code, "remaining", -1);
    }

    @Override
    public void deleteByCode(String code) {
        redisTemplate.delete(PREFIX + code);
    }

    @Override
    public boolean existsCode(String code) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX + code));
    }


}
