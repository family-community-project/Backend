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
    private static final String FAMILY_CODE_PREFIX = "invite:family:";
    private static final Duration EXPIRE_DURATION = Duration.ofDays(1);

    @Override
    public void saveInviteCode(String code, Long familyId, int maxUses) {
        String key = PREFIX + code;
        redisTemplate.opsForHash().put(key, "familyId", String.valueOf(familyId));
        redisTemplate.opsForHash().put(key, "remaining", String.valueOf(maxUses));
        redisTemplate.expire(key, EXPIRE_DURATION);

        redisTemplate.opsForValue().set(FAMILY_CODE_PREFIX + familyId, code, EXPIRE_DURATION);
    }

    public Optional<String> findExistingCodeByFamilyId(Long familyId) {
        String code = redisTemplate.opsForValue().get(FAMILY_CODE_PREFIX + familyId);
        return Optional.ofNullable(code);
    }

    public String findFamilyIdByCode(String code) {
        return (String) redisTemplate.opsForHash().get(PREFIX + code, "familyId");
    }

    public Integer findRemainingUsesByCode(String code) {
        String value = (String) redisTemplate.opsForHash().get(PREFIX + code, "remaining");
        return value != null ? Integer.parseInt(value) : null;
    }

    public void decreaseRemainingUses(String code) {
        String key = PREFIX + code;
        redisTemplate.opsForHash().increment(key, "remaining", -1);
    }

    public void deleteByCode(String code) {
        redisTemplate.delete(PREFIX + code);
    }

    public boolean existsCode(String code) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX + code));
    }

    public boolean isCodeUsable(String code) {
        if (!existsCode(code)) return false;
        Integer remaining = findRemainingUsesByCode(code);
        return remaining != null && remaining > 0;
    }

    public Long getRemainingTtlSeconds(String code) {
        return redisTemplate.getExpire(PREFIX + code);
    }

}
