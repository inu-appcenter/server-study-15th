package com.example.todolist.service;

import com.example.todolist.domain.Member;
import com.example.todolist.domain.Message;
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

    private static final String NOT_FOUND_MEMBER_MESSAGE = "회원정보가 존재하지 않습니다.";
    private static final String SUCCESS_MEMBER_SAVE_MESSAGE = "회원정보가 저장되었습니다.";

    @Transactional
    public Message save(MemberReqDto memberReqDto) {
        Member member = memberReqDto.toMember(memberReqDto);
        Member savedMember = memberRepository.save(member);
        return new Message(SUCCESS_MEMBER_SAVE_MESSAGE);
    }

    @Transactional
    public Long update(Long id, MemberReqDto memberReqDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException(NOT_FOUND_MEMBER_MESSAGE));
        Member updateMember = member.toMember(memberReqDto);
        return updateMember.getId();
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
