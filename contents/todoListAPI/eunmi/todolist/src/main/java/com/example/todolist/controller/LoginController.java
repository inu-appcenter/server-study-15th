package com.example.todolist.controller;

import com.example.todolist.domain.Message;
import com.example.todolist.dto.LoginReqDto;
import com.example.todolist.dto.SignupReqDto;
import com.example.todolist.service.LoginService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private static final String SUCCESS_MEMBER_LOGIN_MESSAGE = "로그인 성공";
    private static final String SUCCESS_MEMBER_CREATE_MESSAGE = "회원가입을 성공했습니다.";

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginReqDto loginReqDto) {
        String token = loginService.login(loginReqDto);
        log.info(SUCCESS_MEMBER_LOGIN_MESSAGE);
        return ResponseEntity.status(HttpStatus.OK)
                .body(token);
    }

    @ApiOperation(value = "회원 가입")
    @PostMapping("/signup")
    public ResponseEntity<Message> join(@Valid @RequestBody SignupReqDto signupReqDto) {
        loginService.join(signupReqDto);
        log.info(SUCCESS_MEMBER_CREATE_MESSAGE);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Message(SUCCESS_MEMBER_CREATE_MESSAGE));
    }
}