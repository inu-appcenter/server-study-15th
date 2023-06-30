package com.example.todolist.service;

import com.example.todolist.exception.NotFoundException;
import com.example.todolist.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {  // userName을 가지고 UserDetails 객체를 반환
        log.info("[loadUserByUsername] username : {}", username);
        return memberRepository.findByUserName(username)
                .orElseThrow(() -> new NotFoundException("해당 userName을 찾을 수 없습니다."));
    }
}
