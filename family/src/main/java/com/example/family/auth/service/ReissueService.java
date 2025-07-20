package com.example.family.auth.service;

import com.example.family.auth.entity.dto.response.TokenResponse;
import com.example.family.config.JwtProperties;
import com.example.family.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReissueService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final JwtProperties jwtProperties;


    public TokenResponse reissue(String authHeader, String refreshToken) {
        String accessToken = extractAccessToken(authHeader);
        Long userId = tokenProvider.getUserIdFromToken(accessToken);

        boolean isValid = refreshTokenService.validateRefreshToken(userId, refreshToken);
        if (!isValid) {
            throw new UnauthorizedException("refresh Token이 유효하지 않습니다.");
        }

        String newAccessToken = tokenProvider.createAccessToken(userId);
        String newRefreshToken = tokenProvider.createRefreshToken(userId);

        refreshTokenService.saveRefreshToken(
                userId,
                newRefreshToken,
                jwtProperties.getRefreshTokenValidityInSeconds()
        );

        return new TokenResponse(newAccessToken, newRefreshToken);

    }

    private String extractAccessToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("헤더 형식이 잘못되었습니다.");
        }
        return authHeader.substring(7);
    }
}
