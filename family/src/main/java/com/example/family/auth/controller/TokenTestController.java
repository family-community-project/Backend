package com.example.family.auth.controller;

import com.example.family.auth.entity.dto.response.TokenResponse;
import com.example.family.auth.service.RefreshTokenService;
import com.example.family.auth.service.TokenProvider;
import com.example.family.config.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class TokenTestController {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final JwtProperties jwtProperties;

    @GetMapping("/generate")
    public ResponseEntity<String> generateToken(@RequestParam Long userId) {
        String token = tokenProvider.createAccessToken(userId);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/generate-both")
    public ResponseEntity<TokenResponse> generateBothTokens(@RequestParam Long userId) {
        String accessToken = tokenProvider.createAccessToken(userId);
        String refreshToken = tokenProvider.createRefreshToken(userId);

        // Redis 저장
        refreshTokenService.saveRefreshToken(
                userId,
                refreshToken,
                jwtProperties.getRefreshTokenValidityInSeconds()
        );

        return ResponseEntity.ok(new TokenResponse(accessToken, refreshToken));
    }
}
