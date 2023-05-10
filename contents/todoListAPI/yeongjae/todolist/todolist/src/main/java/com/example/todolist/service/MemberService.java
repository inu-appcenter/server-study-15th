package com.example.todolist.service;

import com.example.todolist.domain.User;
import com.example.todolist.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long join(User user) {
        memberRepository.save(user);
        return user.getId();
    }
}
