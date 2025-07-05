package com.example.family.auth.controller;

import com.example.family.auth.entity.KakaoToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/api")
public class AuthController {

    @Value("${auth.kakao.client_id}")
    String CLIENT_ID;

    @Value("${auth.kakao.client_secret}")
    String CLIENT_SECRET;

    @Value("${auth.kakao.redirect_uri}")
    String REDIRECT_URI;

    @Value("${auth.kakao.authorize_uri}")
    String AUTHORIZE_URI;

    @Value("${auth.kakao.token_uri}")
    String TOKEN_URI;

    @GetMapping("/auth/kakao")
    public String getAuthKakao() {

        // LOGIN 요청 받아서 처리 후 토큰 리턴
        String uri = "redirect:" + AUTHORIZE_URI +
                     "?response_type=code" +
                     "&client_id=" + CLIENT_ID +
                     "&redirect_uri=" + REDIRECT_URI;
        return uri;
    }

    @GetMapping("/auth/token")
    public String getAuthToken(@RequestParam("code") String code) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", CLIENT_ID);
        params.add("client_secret", CLIENT_SECRET);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("code", code);


        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<KakaoToken> responseEntity = restTemplate.exchange(TOKEN_URI, HttpMethod.POST, request, KakaoToken.class);

        // token
        KakaoToken response = responseEntity.getBody();

        return "none";
    }
}