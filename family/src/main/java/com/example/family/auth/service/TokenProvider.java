package com.example.family.auth.service;

import com.example.family.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;


@RequiredArgsConstructor
@Component
public class TokenProvider {

    private final JwtProperties jwtProperties;
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKeyBytes());
    }

    public String createAccessToken(Long userId) {
        return createToken(userId, jwtProperties.getAccessTokenValidityInSeconds());
    }

    public String createRefreshToken(Long userId) {
        return createToken(userId, jwtProperties.getRefreshTokenValidityInSeconds());
    }

    public String createToken(Long userId, long expiration) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(String.valueOf(userId)) // 사용자 ID 설정
                .setIssuedAt(now) // 발급 시간 설정
                .setExpiration(expireDate) // 만료 시간 설정
                .signWith(secretKey, SignatureAlgorithm.HS256) // 서명 생성
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }

    }

    public Long getUserIdFromToken(String token) {
        return Long.parseLong(Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
    }


    public String createInviteToken(Long familyId, int maxUses) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtProperties.getInviteTokenValidityInSeconds());

        return Jwts.builder()
                .setSubject("invite")
                .claim("familyId", familyId)
                .claim("maxUses", maxUses)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateInviteToken(String token) {
        try {
            Claims claims = getClaims(token);
            return "invite".equals(claims.getSubject()); // 주제 확인
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
