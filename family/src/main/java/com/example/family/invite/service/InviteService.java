package com.example.family.invite.service;

import com.example.family.exception.UserNotFoundException;
import com.example.family.invite.repository.InviteRepository;
import com.example.family.user.entity.Family;
import com.example.family.user.entity.User;
import com.example.family.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final InviteRepository inviteRepository;
    private final UserRepository userRepository;

    public void joinFamilyByCode(String code, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        if (user.getFamily() != null) {
            throw new IllegalStateException("이미 가족 그룹에 속해 있는 사용자입니다.");
        }

        Long familyId = validateInviteCode(code);
        Family family = Family.builder().id(familyId).build();
        user.setFamily(family);
        userRepository.save(user);

        inviteRepository.decreaseRemainingUses(code);
        deleteInviteCode(code);
    }

    private Long validateInviteCode(String code) {
        String familyID = inviteRepository.findFamilyIdByCode(code);
        Integer remaining = inviteRepository.findRemainingUsesByCode(code);

        if (familyID == null || remaining == null || remaining <= 0) {
            throw new IllegalArgumentException("유효하지 않은 초대 코드입니다.");
        }

        return Long.parseLong(familyID);
    }

    private void deleteInviteCode(String code) {
        Integer remaining = inviteRepository.findRemainingUsesByCode(code);
        if (remaining == null || remaining <= 0) {
            inviteRepository.deleteByCode(code);
        }
    }

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
