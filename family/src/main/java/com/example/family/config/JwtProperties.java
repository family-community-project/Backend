package com.example.family.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String header;
    private String secret;
    private long tokenValidityInSeconds;

    public byte[] getSecretKeyBytes() {
        return Base64.getDecoder().decode(secret);
    }
}
