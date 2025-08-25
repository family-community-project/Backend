package com.example.family.invite.controller;

import com.example.family.auth.service.TokenProvider;
import com.example.family.invite.dto.request.InviteCreateRq;
import com.example.family.invite.dto.response.InviteCreateRs;
import com.example.family.invite.service.InviteService;
import com.example.family.user.entity.User;
import com.example.family.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invite")
@RequiredArgsConstructor
public class InviteController {
    private final InviteService inviteService;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;


    @PostMapping("/create")
    public ResponseEntity<InviteCreateRs> createInvite(@RequestHeader("Authorization") String authHeader,
                                                       @RequestBody InviteCreateRq request) {
        Long userId = extractUserIdFromToken(authHeader);
        User user = userRepository.findById(userId).orElseThrow();

        String url = inviteService.generateInviteUrl(user.getFamily().getId(), request.getCount());
        return ResponseEntity.ok(new InviteCreateRs(url));
    }

    @PostMapping("/join")
    public ResponseEntity<String> joinByCode(@RequestParam String code,
                                             @RequestHeader("Authorization") String authHeader) {
        Long userId = extractUserIdFromToken(authHeader);
        inviteService.joinFamilyByCode(code, userId);
        return ResponseEntity.ok("가족 그룹 참여 완료");
    }

    private Long extractUserIdFromToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization 헤더가 유효하지 않습니다.");
        }
        String token = authHeader.substring(7);
        return tokenProvider.getUserIdFromToken(token);
    }
}


