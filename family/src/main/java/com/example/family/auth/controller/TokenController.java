package com.example.family.auth.controller;

import com.example.family.auth.entity.dto.response.TokenResponse;
import com.example.family.auth.service.ReissueService;
import com.example.family.auth.util.CookieUtil;
import com.example.family.config.JwtProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/token")
public class TokenController {

    private final ReissueService reissueService;
    private final JwtProperties jwtProperties;

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reissueToken(@RequestHeader("Authorization") String authHeader,
                                                      HttpServletRequest request,
                                                      HttpServletResponse response) {
        String refreshToken = extractRefreshTokenFromCookies(request);
        TokenResponse tokenResponse = reissueService.reissue(authHeader, refreshToken);

        CookieUtil.addCookie(
                response,
                tokenResponse.getRefreshToken(),
                (int) (jwtProperties.getRefreshTokenValidityInSeconds() / 1000)
        );
        return ResponseEntity.ok(tokenResponse);
    }

    private String extractRefreshTokenFromCookies(HttpServletRequest request) {
        String refreshToken = CookieUtil.getCookie(request, "refreshToken");
        if (refreshToken == null) {
            throw new RuntimeException("Refresh Token 쿠키가 존재하지 않습니다.");
        }
        return refreshToken;
    }
}
