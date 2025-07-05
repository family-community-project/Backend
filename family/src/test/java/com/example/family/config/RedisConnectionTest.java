package com.example.family.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RedisConnectionTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void redisConnectionTest() {
        //given
        String key = "test";
        String value = "hello";

        //when
        redisTemplate.opsForValue().set(key, value);
        String result = redisTemplate.opsForValue().get(key);

        //then
        Assertions.assertEquals(value, result);
    }
}
