package com.example.todolist.service;

import com.example.todolist.domain.Member;
import com.example.todolist.dto.MemberDto;
import com.example.todolist.exception.DuplicationException;
import com.example.todolist.exception.NotFoundMemberException;
import com.example.todolist.exception.NotFoundMemberException;
import com.example.todolist.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final String NOT_FOUND_MEMBER_MESSAGE = "회원정보가 존재하지 않습니다.";

    @Transactional
    public Long save(MemberDto memberDto) {
        Member member = memberDto.toMember(memberDto);
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    @Transactional
    public Long update(Long id, MemberDto memberDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException(NOT_FOUND_MEMBER_MESSAGE));
        member.updateMember(memberDto);
        return member.getId();
    }

    public MemberDto findOne(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException(NOT_FOUND_MEMBER_MESSAGE));
        return MemberDto.builder()
                .name(member.getName())
                .nickName(member.getNickName())
                .email(member.getEmail())
                .build();
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

}
