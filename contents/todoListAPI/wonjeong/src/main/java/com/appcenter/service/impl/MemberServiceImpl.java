package com.appcenter.service.impl;

import com.appcenter.data.dto.MemberDTO;
import com.appcenter.data.dto.response.MemberResponseDTO;
import com.appcenter.data.entity.Member;
import com.appcenter.data.repository.MemberRepository;
import com.appcenter.service.MemberService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    // MemberRepository를 상수로 선언
    private final MemberRepository memberRepository;
    // 오류 메세지 상수 선언
    private final String NOT_FOUND_MEMBER = "유효하지 않은 멤버 번호 입니다.";

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public MemberResponseDTO getMember(Long id) throws Exception{
        // 나중에 getMember 인터페이스에 예외처리 추가
        Optional<Member> member = memberRepository.findById(id);

        if(member.isPresent()) {
            Member content = member.get();
            // 리턴할 새로운 DTO 객체 생성
            // Entity 객체에서 get해서 DTO 객체에 set
            MemberResponseDTO memberResponseDTO = new MemberResponseDTO();
            memberResponseDTO.setId(content.getId());
            memberResponseDTO.setName(content.getName());
            memberResponseDTO.setPassword(content.getPassword());
            memberResponseDTO.setEmail(content.getEmail());

            return memberResponseDTO;
        } else {
            throw new Exception(NOT_FOUND_MEMBER);
        }
    }

    @Override
    public MemberResponseDTO savedMember(MemberDTO memberDTO) {
        Member member = new Member();
        member.setName(memberDTO.getName());
        member.setPassword(memberDTO.getPassword());
        member.setEmail(memberDTO.getEmail());

        Member savedMember = memberRepository.save(member);

        MemberResponseDTO memberResponseDTO = new MemberResponseDTO();
        memberResponseDTO.setId(savedMember.getId());
        memberResponseDTO.setName(savedMember.getName());
        memberResponseDTO.setPassword(savedMember.getPassword());
        memberResponseDTO.setEmail(savedMember.getEmail());

        return memberResponseDTO;
    }

    @Override
    public MemberResponseDTO changeMemberinfo(Long id, String name) throws Exception {
        // 멤버 수정 기능은 재 정의 해야 함
        Member foundMember = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MEMBER));

        foundMember.setName(name);

        Member changedMember = memberRepository.save(foundMember);

        // DTO 객체에 담아 리턴
        // DTO 객체 선언
        MemberResponseDTO memberResponseDTO = new MemberResponseDTO();
        memberResponseDTO.setId(changedMember.getId());
        memberResponseDTO.setName(changedMember.getName());
        memberResponseDTO.setPassword(changedMember.getPassword());
        memberResponseDTO.setEmail(changedMember.getEmail());

        return memberResponseDTO;
    }

    @Override
    public void deleteMember(Long id) throws Exception {
        // DAO에서 delete 메소드 호출
        try {
            memberRepository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException(NOT_FOUND_MEMBER);
        }
    }
}
