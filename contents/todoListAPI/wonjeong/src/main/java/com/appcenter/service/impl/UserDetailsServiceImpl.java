package com.appcenter.service.impl;

import com.appcenter.data.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// UserDetailsService 인터페이스를 구현하는 서비스 객체
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    // UserDetails는 시큐리티에서 제공하는 개념
    @Override
    public UserDetails loadUserByUsername(String username) {
        return memberRepository.getByUid(username);
    }
}
