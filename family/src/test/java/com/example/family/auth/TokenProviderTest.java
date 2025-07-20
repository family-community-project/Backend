package com.example.family.auth;

import com.example.family.auth.service.TokenProvider;
import com.example.family.config.JwtProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Test
    void 토큰_생성_확인() {
        //given
        Long userId = 5L;
        //when
        String token = tokenProvider.createAccessToken(userId);
        assertNotNull(token);
        System.out.println("토큰: " + token);

    }
}
