package com.example.family.invite;

import com.example.family.invite.repository.RedisInviteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class RedisInviteRepositoryTest {
    @Autowired
    private RedisInviteRepository redisInviteRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String TEST_CODE = "test1234";
    private final String testCode = "49dc9076";

//    @BeforeEach
//    void setUp() {
//        // 초기화: 테스트 시작 전 값 세팅
//        redisInviteRepository.saveInviteCode(testCode, 2L, 0);
//    }

    @Test
    void 초대코드_만료_확인() throws InterruptedException {
        // given: 유효 시간 짧게 설정된 RedisTemplate 사용 or 수동 expire 설정
        String code = "expire-test-code";
        redisInviteRepository.saveInviteCode(code, 2L, 5);

        // Redis에서 직접 만료 시간 재설정 (테스트 용도)
        redisTemplate.expire("invite:" + code, Duration.ofSeconds(1));

        // when: 1초 대기 후 조회 시도
        Thread.sleep(1500); // 1.5초 대기하여 만료 유도

        // then: 키가 없어야 함
        boolean exists = redisInviteRepository.existsCode(code);
        assertFalse(exists, "만료된 초대코드는 존재하지 않아야 합니다.");
    }


    @Test
    void 초대코드_remaining값_감소_확인() {
        // given
        Integer before = redisInviteRepository.findRemainingUsesByCode(testCode);

        // when
        redisInviteRepository.decreaseRemainingUses(testCode);

        // then
        Integer after = redisInviteRepository.findRemainingUsesByCode(testCode);
        assertNotNull(before);
        assertNotNull(after);
        assertEquals(before - 1, after);
    }


//    @BeforeEach
//    void setUp() {
//        // 정상이 아닌 값 저장 (에러 유발)
//        redisTemplate.opsForHash().put("invite:" + TEST_CODE, "remaining", "not-a-number");
//    }

//    @AfterEach
//    void tearDown() {
//        redisTemplate.delete("invite:" + TEST_CODE);
//    }
//
//    @Test
//    void remaining값이_정수가_아닐경우_increment_실패_확인() {
//        // when & then
//        assertThrows(RedisSystemException.class, () -> {
//            redisInviteRepository.decreaseRemainingUses(TEST_CODE);
//        });
//    }
}
