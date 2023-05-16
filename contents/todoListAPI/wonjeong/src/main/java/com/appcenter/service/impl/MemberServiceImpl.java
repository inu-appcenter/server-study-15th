package com.appcenter.service.impl;

import com.appcenter.data.dto.MemberDTO;
import com.appcenter.data.dto.response.MemberResponseDTO;
import com.appcenter.data.entity.Member;
import com.appcenter.data.repository.MemberRepository;
import com.appcenter.service.MemberService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    // MemberRepository를 상수로 선언
    private final MemberRepository memberRepository;
    // 오류 메세지 상수 선언
    private final String NOT_FOUND_MEMBER = "유효하지 않은 멤버 번호 입니다.";

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public MemberResponseDTO getMember(Long number){
        // 나중에 getMember 인터페이스에 예외처리 추가
        Member member = memberRepository.findById(number).get();

        // 리턴할 새로운 DTO 객체 생성
        // Entity 객체에서 get해서 DTO 객체에 set
        MemberResponseDTO memberResponseDTO = new MemberResponseDTO();
        memberResponseDTO.setNumber(member.getNumber());
        memberResponseDTO.setName(member.getName());
        memberResponseDTO.setPassword(member.getPassword());
        memberResponseDTO.setEmail(member.getEmail());

        return memberResponseDTO;
    }

    @Override
    public MemberResponseDTO savedMember(MemberDTO memberDTO) {
        Member member = new Member();
        member.setName(memberDTO.getName());
        member.setPassword(memberDTO.getPassword());
        member.setEmail(memberDTO.getEmail());

        Member savedMember = memberRepository.save(member);

        MemberResponseDTO memberResponseDTO = new MemberResponseDTO();
        memberResponseDTO.setNumber(savedMember.getNumber());
        memberResponseDTO.setName(savedMember.getName());
        memberResponseDTO.setPassword(savedMember.getPassword());
        memberResponseDTO.setEmail(savedMember.getEmail());

        return memberResponseDTO;
    }

    @Override
    public MemberResponseDTO changeMemberinfo(Long number, String name) throws Exception {
        // 멤버 수정 기능은 재 정의 해야 함
        Member foundMember = memberRepository.findById(number)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MEMBER));

        foundMember.setName(name);

        Member changedMember = memberRepository.save(foundMember);

        // DTO 객체에 담아 리턴
        // DTO 객체 선언
        MemberResponseDTO memberResponseDTO = new MemberResponseDTO();
        memberResponseDTO.setNumber(changedMember.getNumber());
        memberResponseDTO.setName(changedMember.getName());
        memberResponseDTO.setPassword(changedMember.getPassword());
        memberResponseDTO.setEmail(changedMember.getEmail());

        return memberResponseDTO;
    }

    @Override
    public void deleteMember(Long number) throws Exception {
        // DAO에서 delete 메소드 호출
        try {
            memberRepository.deleteById(number);
        } catch (Exception e) {
            throw new NotFoundException(NOT_FOUND_MEMBER);
        }
    }
}
