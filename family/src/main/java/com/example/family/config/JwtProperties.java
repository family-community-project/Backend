package com.example.family.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String header;
    private String secret;
    private long accessTokenValidityInSeconds;
    private long refreshTokenValidityInSeconds;

    public byte[] getSecretKeyBytes() {
        return Base64.getDecoder().decode(secret);
    }

    public long getAccessTokenValidityInSeconds() {
        return accessTokenValidityInSeconds * 1000L;
    }

    public long getRefreshTokenValidityInSeconds() {
        return refreshTokenValidityInSeconds * 1000L;
    }
}
