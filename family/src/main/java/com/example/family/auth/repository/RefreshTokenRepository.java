package com.example.family.auth.repository;

public interface RefreshTokenRepository {
    void saveRefreshToken(String userId, String refreshToken, long expiration);
    String getRefreshToken(String userId);
    void deleteRefreshToken(String userId);

}
