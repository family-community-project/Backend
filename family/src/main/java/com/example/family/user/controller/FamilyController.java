package com.example.family.user.controller;

import com.example.family.config.interceptor.AuthService;
import com.example.family.user.dto.request.FamilyCreateRq;
import com.example.family.user.entity.Family;
import com.example.family.user.service.FamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/family")
@RequiredArgsConstructor
public class FamilyController {
    private final FamilyService familyService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<?> createFamily(@RequestHeader("Authorization")String authHeader, @RequestBody FamilyCreateRq request) {
        Long userId = authService.getAuthenticatedUserId(authHeader);
        Family family = familyService.createFamily(request, userId);

        return ResponseEntity.ok(Map.of("familyId", family.getId(), "familyName", family.getFamilyName()));
    }

}
