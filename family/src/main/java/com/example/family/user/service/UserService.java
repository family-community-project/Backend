package com.example.family.user.service;

import com.example.family.user.dto.request.UserRegisterRequest;
import com.example.family.user.entity.LoginType;
import com.example.family.user.entity.User;
import com.example.family.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Long registerUser(UserRegisterRequest request) {
        String email = request.getEmail();
        String name = request.getName();
        String password = request.getPassword();

        User user = User.builder()
                .email(email)
                .name(name)
                .password(password)
                .loginType(LoginType.LOCAL)
                .build();
        return userRepository.save(user).getId();
    }
}
