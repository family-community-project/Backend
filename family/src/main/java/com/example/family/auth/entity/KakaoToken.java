package com.example.family.auth.entity;

import lombok.Getter;

@Getter
public class KakaoToken {
    String access_token;
    String token_type;
    String refresh_token;
    String id_token;
    int expires_in;
    int refresh_token_expires_in;
}
