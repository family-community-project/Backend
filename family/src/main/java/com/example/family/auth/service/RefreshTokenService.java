package com.example.family.auth.service;

import com.example.family.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public void saveRefreshToken(Long userId, String refreshToken, long expiration) {
        refreshTokenRepository.saveRefreshToken(userId.toString(), refreshToken, expiration);
    }

    public String getRefreshToken(Long userId) {
        return refreshTokenRepository.getRefreshToken(userId.toString());
    }

    public boolean validateRefreshToken(Long userId, String refreshToken) {
        String stored = refreshTokenRepository.getRefreshToken(userId.toString());
        if (stored == null) {
            return false;
        }
        return stored.equals(refreshToken) && tokenProvider.validateToken(refreshToken);
    }

    public void deleteRefreshToken(Long userId) {
        refreshTokenRepository.deleteRefreshToken(userId.toString());
    }
}
