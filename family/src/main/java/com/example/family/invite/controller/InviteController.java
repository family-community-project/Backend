package com.example.family.invite.controller;

import com.example.family.config.interceptor.AuthService;
import com.example.family.invite.dto.request.InviteCreateRq;
import com.example.family.invite.dto.request.InviteCreateRs;
import com.example.family.invite.service.InviteService;
import com.example.family.user.entity.User;
import com.example.family.user.service.FamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invite")
@RequiredArgsConstructor
public class InviteController {
    private final InviteService inviteService;
    private final AuthService authService;
    private final FamilyService familyService;

    @PostMapping("/create")
    public InviteCreateRs createInviteCode(@RequestHeader("Authorization") String authHeader, @RequestBody InviteCreateRq request) {
        User user = authService.findAuthenticatedUser(authHeader);
        String code = inviteService.getOrCreateInviteCode(user.getId(), request.getCount());
        return new InviteCreateRs(code);
    }

    @PostMapping("/join")
    public ResponseEntity<Void> joinFamilyGroup(@RequestHeader("Authorization") String authHeader, @RequestParam String code) {
        User user = authService.findAuthenticatedUser(authHeader);
        inviteService.joinFamilyByCode(code, user.getId());
        return ResponseEntity.ok().build();
    }

}
