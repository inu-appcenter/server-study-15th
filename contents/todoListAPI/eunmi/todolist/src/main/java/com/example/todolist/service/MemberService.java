package com.example.todolist.service;

import com.example.todolist.domain.Member;
import com.example.todolist.dto.MemberPageRespDto;
import com.example.todolist.dto.MemberRespDto;
import com.example.todolist.dto.UpdateMemberReqDto;
import com.example.todolist.exception.NotFoundException;
import com.example.todolist.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String NOT_FOUND_MEMBER_MESSAGE = "회원정보가 존재하지 않습니다.";

    @Transactional
    public void update(Long id, UpdateMemberReqDto updateMemberReqDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MEMBER_MESSAGE));
        member.updateMember(updateMemberReqDto);
    }

    public MemberRespDto findOne(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MEMBER_MESSAGE));
        return member.toMemberRespDto(member);
    }

    public List<MemberPageRespDto> findAll() {
        List<Member> members = memberRepository.findAll();
        return members.stream().map(member -> member.toMemberPageRespDto(member))
                .collect(Collectors.toList());
    }

    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MEMBER_MESSAGE));
        memberRepository.deleteById(id);
    }
}
