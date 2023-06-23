package com.appcenter.service.impl;

import com.appcenter.common.CommonResponse;
import com.appcenter.data.dto.result.SignInResultDTO;
import com.appcenter.data.dto.result.SignUpResultDTO;
import com.appcenter.data.entity.Member;
import com.appcenter.data.repository.MemberRepository;
import com.appcenter.security.JwtTokenProvider;
import com.appcenter.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SignServiceImpl implements SignService {
    public MemberRepository memberRepository;
    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;

    @Autowired
    public SignServiceImpl(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider,
                           PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        // passwordEncoder는 Spring Security config에 같이 @bean해도 괜찮음
        this.passwordEncoder = passwordEncoder;
    }

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
        Member user = memberRepository.getByUid(id);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException();
        }

        SignInResultDTO signInResultDTO = SignInResultDTO.builder()
                .token(jwtTokenProvider.createToken(String.valueOf(user.getUid()), user.getRoles()))
                .build();

        setSuccessResult(signInResultDTO);

        return signInResultDTO;
    }

    private void setSuccessResult(SignUpResultDTO result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
        System.out.println("SignUpResultDTO를 성공적으로 생성");
    }

    private void setFailResult(SignUpResultDTO result) {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }
}
