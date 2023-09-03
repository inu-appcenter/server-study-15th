package com.appcenter.service.impl;

import com.appcenter.data.dto.common.CommonResponse;
import com.appcenter.data.dto.result.SignInResultDTO;
import com.appcenter.data.dto.result.SignUpResultDTO;
import com.appcenter.data.entity.Member;
import com.appcenter.data.repository.MemberRepository;
import com.appcenter.security.JwtTokenProvider;
import com.appcenter.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {
    public final MemberRepository memberRepository;
    public final JwtTokenProvider jwtTokenProvider;
    public final PasswordEncoder passwordEncoder;

    @Override
    public SignUpResultDTO signUp(String id, String password, String name, String role) {
        Member member;
        if (role.equalsIgnoreCase("admin")) {
            member = Member.builder()
                    .uid(id)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .roles(Collections.singletonList("ROLE_ADMIN"))
                    .build();
        } else {
            member = Member.builder()
                    .uid(id)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }

        Member savedUser = memberRepository.save(member);

        SignUpResultDTO signUpResultDTO = new SignUpResultDTO();

        if (!savedUser.getName().isEmpty()) {
            setSuccessResult(signUpResultDTO);
        } else {
            setFailResult(signUpResultDTO);
        }
        return signUpResultDTO;
    }

    @Override
    public SignInResultDTO signIn(String id, String password) throws RuntimeException {
        Member member = memberRepository.getByUid(id);

        // 컨트롤러를 통해 제공된 password와 DB에 저장된 패스워드가 다르면 오류를 던짐
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new RuntimeException();
        }

        SignInResultDTO signInResultDTO = SignInResultDTO.builder()
                .token(jwtTokenProvider.createToken(String.valueOf(member.getUid()), member.getRoles()))
                .build();

        setSuccessResult(signInResultDTO);

        return signInResultDTO;
    }

    private void setSuccessResult(SignUpResultDTO result) {
        result.updateSignUpResultDTO(true, CommonResponse.SUCCESS.getCode(), CommonResponse.SUCCESS.getMsg());
    }

    private void setFailResult(SignUpResultDTO result) {
        result.updateSignUpResultDTO(false, CommonResponse.FAIL.getCode(), CommonResponse.FAIL.getMsg());
    }
}
