package com.appcenter.service.impl;

import com.appcenter.data.dto.request.MemberRequestDTO;
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
    public MemberResponseDTO savedMember(MemberRequestDTO memberRequestDTO) {
        Member member = new Member().createMember(memberRequestDTO);

        Member savedMember = memberRepository.save(member);
        // 선언된 DTO 객체에 updateMemberResponse 메서드를 이용해 정보를 담아 리턴
        return new MemberResponseDTO().updateMemberResponse(savedMember);
    }

    @Override
    public MemberResponseDTO updateMember(Long id, MemberRequestDTO memberRequestDTO) throws Exception {
        Member foundMember = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MEMBER));

        Member changedMember = memberRepository.save(foundMember.updateMember(id, memberRequestDTO));

        return new MemberResponseDTO().updateMemberResponse(changedMember);
    }

    @Override
    public void deleteMember(Long id) throws Exception {
        try {
            memberRepository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException(NOT_FOUND_MEMBER);
        }
    }
}
