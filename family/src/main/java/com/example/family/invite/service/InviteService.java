package com.example.family.invite.service;

import com.example.family.invite.repository.InviteRepository;
import com.example.family.invite.repository.RedisInviteRepository;
import com.example.family.user.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final InviteRepository inviteRepository;

    public String getOrCreateInviteCode(Long familyId, int maxUses) {
        return inviteRepository.findExistingCodeByFamilyId(familyId)
                .filter(code -> isUsable(code))
                .orElseGet(() -> createInviteCode(familyId, maxUses));
    }

    private boolean isUsable(String code) {
        Integer remaining = inviteRepository.findRemainingUsesByCode(code);
        return remaining != null && remaining > 0;
    }

    public String createInviteCode(Long familyId, int maxUses) {
        String code = generateInviteCode();
        inviteRepository.saveInviteCode(code, familyId, maxUses);
        return code;
    }

    private String generateInviteCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }
}
