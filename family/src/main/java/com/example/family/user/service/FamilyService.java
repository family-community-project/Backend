package com.example.family.user.service;

import com.example.family.user.dto.request.FamilyCreateRq;
import com.example.family.user.entity.Family;
import com.example.family.user.entity.User;
import com.example.family.user.repository.FamilyRepository;
import com.example.family.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FamilyService {
    private final FamilyRepository familyRepository;
    private final UserRepository userRepository;

    public Family createFamily(FamilyCreateRq request, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (user.getFamily() != null) {
            throw new IllegalStateException("이미 가족 그룹에 속해 있는 사용자입니다.");
        }

        Family family = Family.builder()
                .familyName(request.getFamilyName())
                .owner(user)
                .build();

        Family familyGroup = familyRepository.save(family);

        user.setFamily(family);
        userRepository.save(user);

        return familyGroup;
    }
}
