package com.example.family.invite;

import com.example.family.invite.service.InviteService;
import com.example.family.user.entity.LoginType;
import com.example.family.user.entity.User;
import com.example.family.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Rollback
@Transactional
@ActiveProfiles("test")
public class InviteServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InviteService inviteService;

    private User user;

    @BeforeEach
    void setUp() {
        user = userRepository.save(
                User.builder()
                        .name("tester")
                        .loginType(LoginType.LOCAL)
                        .build()
        );
    }

    @Test
    void 초대코드_생성_및_재사용_테스트() {
        String code1 = inviteService.getOrCreateInviteCode(user.getId(), 5);
        String code2 = inviteService.getOrCreateInviteCode(user.getId(), 5);

        assertEquals(code1, code2); // 재사용 확인
    }

}
