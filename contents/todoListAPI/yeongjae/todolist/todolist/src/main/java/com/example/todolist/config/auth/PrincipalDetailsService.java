package com.example.todolist.config.auth;

import com.example.todolist.domain.user.User;
import com.example.todolist.exception.CustomException;
import com.example.todolist.exception.ErrorCode;
import com.example.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.todolist.exception.ErrorCode.USER_NOT_FOUND_EXCEPTION;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService : 진입");
        User user = userRepository.findByUserName(username).orElseThrow(() -> new CustomException(USER_NOT_FOUND_EXCEPTION));

        return new PrincipalDetails(user);
    }
}
