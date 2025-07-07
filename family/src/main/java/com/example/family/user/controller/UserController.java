package com.example.family.user.controller;

import com.example.family.user.dto.request.UserRegisterRequest;
import com.example.family.user.dto.response.AuthMailResponse;
import com.example.family.user.dto.response.CodeCheckResponse;
import com.example.family.user.repository.UserRepository;
import com.example.family.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "Local Login", description = "Local Login API - register, email auth code, etc.")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    private final JavaMailSender javaMailSender;
    private final Map<String, String> emailAuthStringMap = new HashMap<>();
    private final Map<String, Boolean> emailAuthBooleanMap = new HashMap<>();

    @Operation(summary = "계정 등록 API", description = "신규 계정 등록을 위한 API 입니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "register: success or fail"),
            @ApiResponse(responseCode = "500", description = "internal server error"),
    })
    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestParam("email") String email,
                           @RequestParam("name") String name,
                           @RequestParam("password") String password) {
        ResponseEntity<Boolean> responseEntity = new ResponseEntity<>(HttpStatus.OK);

        if (emailAuthBooleanMap.containsKey(email) && emailAuthBooleanMap.get(email)) {
            userService.registerUser(new UserRegisterRequest(email, name, password));
            responseEntity = ResponseEntity.ok(true);
        }

        return responseEntity;
    }

    @Operation(summary = "이메일 인증 코드 전송 API", description = "이메일 인증 코드 전송을 위한 API 입니다. 중복된 이메일이 있을 경우 false를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "email auth: success(true) or already existed(false)"),
            @ApiResponse(responseCode = "500", description = "internal server error"),
    })
    @GetMapping("auth/email")
    public ResponseEntity<AuthMailResponse> authEmail(@RequestParam("email") String email) {

        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.ok(new AuthMailResponse(false, null));
        }

        ResponseEntity<AuthMailResponse> responseEntity;
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String authCode = generateAuthString();
        try {
            mimeMessage.setRecipients(MimeMessage.RecipientType.TO, email);

            mimeMessage.setSubject("이메일 인증");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + authCode + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            mimeMessage.setText(body,"UTF-8", "html");

//            javaMailSender.send(mimeMessage);
            emailAuthStringMap.put(email, authCode);
            responseEntity = ResponseEntity.ok(new AuthMailResponse(true, authCode));
            System.out.println("emailAuthMap: " + emailAuthStringMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new AuthMailResponse(false, null));
        }

        return responseEntity;
    }

    @Operation(summary = "이메일 인증 코드 확인 API", description = "이메일 인증 코드 검증을 위한 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "code check: success(true) or fail(false)"),
            @ApiResponse(responseCode = "500", description = "internal server error"),
    })
    @GetMapping("code/check")
    public ResponseEntity<CodeCheckResponse> authEmailCheck(@RequestParam("email") String email, @RequestParam("code") String authCode) {
        if (emailAuthStringMap.containsKey(email) && emailAuthStringMap.get(email).equals(authCode)) {
            emailAuthBooleanMap.put(email, true);
            return new ResponseEntity<>(new CodeCheckResponse(true), HttpStatus.OK);
        } else {
            emailAuthBooleanMap.put(email, false);
            return new ResponseEntity<>(new CodeCheckResponse(false), HttpStatus.OK);
        }
    }

    private String generateAuthString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}