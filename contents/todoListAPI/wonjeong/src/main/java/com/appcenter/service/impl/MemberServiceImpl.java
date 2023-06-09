package com.appcenter.service.impl;

import com.appcenter.common.CommonResponse;
import com.appcenter.data.dto.request.MemberRequestDTO;
import com.appcenter.data.dto.response.MemberResponseDTO;
import com.appcenter.data.dto.result.SignInResultDTO;
import com.appcenter.data.dto.result.SignUpResultDTO;
import com.appcenter.data.entity.Member;
import com.appcenter.data.repository.MemberRepository;
import com.appcenter.security.JwtTokenProvider;
import com.appcenter.service.MemberService;
import javassist.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MemberServiceImpl implements MemberService {

    // MemberRepository를 상수로 선언
    private final MemberRepository memberRepository;

    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;

    // 오류 메세지 상수 선언
    private final String NOT_FOUND_MEMBER = "유효하지 않은 멤버 id 입니다.";

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public MemberResponseDTO getMember(Long id) throws Exception{
        Member savedMember = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MEMBER));

        return new MemberResponseDTO().updateMemberResponse(savedMember);
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
