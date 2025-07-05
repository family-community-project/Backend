package com.example.family.user.controller;

import com.example.family.user.dto.request.UserRegisterRequest;
import com.example.family.user.service.UserService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final JavaMailSender javaMailSender;
    private final Map<String, String> emailAuthStringMap = new HashMap<>();
    private final Map<String, Boolean> emailAuthBooleanMap = new HashMap<>();

    @PostMapping("/register")
    public String register(@RequestParam("email") String email,
                           @RequestParam("name") String name,
                           @RequestParam("password") String password) {
        String answer = "register fail, email auth not completed";
        if (emailAuthBooleanMap.containsKey(email) && emailAuthBooleanMap.get(email)) {
            Long userId = userService.registerUser(new UserRegisterRequest(email, name, password));
            answer = userId.toString();
        }

        return answer;
    }

    @GetMapping("auth/email")
    public String authEmail(@RequestParam("email") String email) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String authString = generateAuthString();
        try {
            mimeMessage.setRecipients(MimeMessage.RecipientType.TO, email);

            mimeMessage.setSubject("이메일 인증");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + authString + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            mimeMessage.setText(body,"UTF-8", "html");

            javaMailSender.send(mimeMessage);

            emailAuthStringMap.put(email, authString);
            System.out.println("emailAuthMap: " + emailAuthStringMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return authString;
    }

    @GetMapping("auth/email/check")
    public String authEmailCheck(@RequestParam("email") String email, @RequestParam("authString") String authString) {
        if (emailAuthStringMap.containsKey(email) && emailAuthStringMap.get(email).equals(authString)) {
            emailAuthBooleanMap.put(email, true);
            return "success";
        } else {
            emailAuthBooleanMap.put(email, false);
            return "fail";
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