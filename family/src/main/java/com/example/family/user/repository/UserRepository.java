package com.example.family.user.repository;

import com.example.family.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    Optional<User> findByLoginId(String loginId);
//    Optional<User> findByNickname(String nickname);
//    Optional<User> findByLoginIdAndPassword(String loginId, String password);
}
