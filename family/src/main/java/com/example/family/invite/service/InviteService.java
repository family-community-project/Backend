package com.example.family.invite.service;

import com.example.family.invite.repository.InviteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final InviteRepository inviteRepository;
    private static final int CODE_LENGTH = 8;


    public String getOrCreateInviteCode(Long familyId, int maxUses) {
        return inviteRepository.findExistingCodeByFamilyId(familyId)
                .filter(code -> isCodeUsable(code))
                .orElseGet(() -> generateAndSaveNewCode(familyId, maxUses));
    }



    private boolean isCodeUsable(String code) {
        if (!inviteRepository.existsCode(code)) return false;
        Integer remaining = inviteRepository.findRemainingUsesByCode(code);
        return remaining != null && remaining > 0;
    }



    private String generateAndSaveNewCode(Long familyId, int maxUses) {
        String code;
        do {
            code = generateRandomCode(CODE_LENGTH);
        } while (inviteRepository.existsCode(code));


        inviteRepository.saveInviteCode(code, familyId, maxUses);
        return code;
    }


    private String generateRandomCode(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }


    public void joinFamilyByCode(String code, Long userId) {
        if (!inviteRepository.existsCode(code)) {
            throw new IllegalArgumentException("초대 코드가 존재하지 않거나 만료되었습니다.");
        }


        Integer remaining = inviteRepository.findRemainingUsesByCode(code);
        if (remaining == null || remaining <= 0) {
            throw new IllegalStateException("초대 코드 사용 가능 횟수가 초과되었습니다.");
        }


        Long familyId = Long.parseLong(inviteRepository.findFamilyIdByCode(code));

        inviteRepository.decreaseRemainingUses(code);
    }


    public String generateInviteUrl(Long familyId, int maxUses) {
        String code = getOrCreateInviteCode(familyId, maxUses);
        return "https://ttasup.site/invite/" + code;
    }
}
