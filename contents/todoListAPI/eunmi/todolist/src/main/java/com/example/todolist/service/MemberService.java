package com.example.todolist.service;

import com.example.todolist.domain.Member;
import com.example.todolist.dto.MemberReqDto;
import com.example.todolist.dto.MemberPageRespDto;
import com.example.todolist.exception.NotFoundMemberException;
import com.example.todolist.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final String NOT_FOUND_MEMBER_MESSAGE = "회원정보가 존재하지 않습니다.";

    @Transactional
    public Long save(MemberReqDto memberReqDto) {
        Member member = memberReqDto.toMember(memberReqDto);
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    @Transactional
    public Long update(Long id, MemberReqDto memberReqDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException(NOT_FOUND_MEMBER_MESSAGE));
        member.toMember(memberReqDto);
        return member.getId();
    }

    public MemberReqDto findOne(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException(NOT_FOUND_MEMBER_MESSAGE));
        return member.toMemberReqDto(member);
    }

    public List<MemberPageRespDto> findAll() {
        List<Member> members = memberRepository.findAll();
        return members.stream().map(member -> member.toMemberPageRespDto(member))
                .collect(Collectors.toList());
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

}
