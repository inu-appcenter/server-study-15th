package com.example.todolist.service;


import com.example.todolist.config.security.JwtProvider;
import com.example.todolist.domain.Member;
import com.example.todolist.dto.LoginReqDto;
import com.example.todolist.dto.SignupReqDto;
import com.example.todolist.exception.BadRequestException;
import com.example.todolist.exception.NotFoundException;
import com.example.todolist.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignService {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    private final String BAD_REQUEST_LOGIN_ID_MESSAGE = "해당 id의 회원정보가 존재하지 않습니다.";
    private final String BAD_REQUEST_LOGIN_PASSWORD_MESSAGE = "잘못된 password입니다.";
    private final String SUCCESS_LOGIN_ID_MEESAGE = "[login] id 유효성 검증 완료";
    private final String SUCCESS_LOGIN_PASSWORD_MEESAGE = "[login] password 유효성 검증 완료";
    @Transactional
    public String login(LoginReqDto loginReqDto) {
        // 아이디 검사
        Member member = memberRepository.findByUserName(loginReqDto.getId())
                .orElseThrow(() -> new NotFoundException(BAD_REQUEST_LOGIN_ID_MESSAGE));
        log.info(SUCCESS_LOGIN_ID_MEESAGE);

        // 비밀번호 검증
        if (!passwordEncoder.matches(loginReqDto.getPassword(), member.getPassword()))
            throw new BadRequestException(BAD_REQUEST_LOGIN_PASSWORD_MESSAGE);
        log.info(SUCCESS_LOGIN_PASSWORD_MEESAGE);
        return jwtProvider.createJwt(member.getUsername(), String.valueOf(member.getRole()));
    }

    @Transactional
    public void join(SignupReqDto signupReqDto) {
        String password = passwordEncoder.encode(signupReqDto.getPassword());
        log.info("passoword : {}", password);
        Member member = signupReqDto.toMember(signupReqDto, password);
        Member savedMember = memberRepository.save(member);
    }

}
