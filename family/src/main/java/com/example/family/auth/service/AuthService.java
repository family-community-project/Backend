package com.example.family.auth.service;

import com.example.family.exception.UserNotFoundException;
import com.example.family.user.entity.User;
import com.example.family.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

@Service
@RequiredArgsConstructor
public class AuthService implements HandlerInterceptor {
    private final RedisTemplate redisTemplate;
    private final UserRepository userRepository;
    private final TokenResolver tokenResolver;

    public Long getAuthenticatedUserId(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("헤더 형식이 잘못되었습니다.");
        }

        String token = authHeader.substring(7);
        return tokenResolver.getUserIdFromAccessToken(token);
    }

    public User findAuthenticatedUser(String authHeader) {
        Long userId = getAuthenticatedUserId(authHeader);
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found"));
    }

}
