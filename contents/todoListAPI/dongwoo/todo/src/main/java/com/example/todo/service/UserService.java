package com.example.todo.service;

import com.example.todo.common.exception.user.AlreadyExistUserException;
import com.example.todo.domain.User;
import com.example.todo.dto.request.GeneralSignUpInfo;
import com.example.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(GeneralSignUpInfo signUpInfo) {

        log.info(signUpInfo.getEmail());

        if (userRepository.existsByEmail(signUpInfo.getEmail())) {
            throw new AlreadyExistUserException();
        }

        User user = User.builder()
                .name(signUpInfo.getName())
                .email(signUpInfo.getEmail())
                .password(passwordEncoder.encode(signUpInfo.getPassword()))
                .build();

        userRepository.save(user);
    }
}
