package com.appcenter.controller;

import com.appcenter.data.dto.result.SignInResultDTO;
import com.appcenter.data.dto.result.SignUpResultDTO;
import com.appcenter.data.entity.Member;
import com.appcenter.service.MemberService;
import com.appcenter.service.impl.SignServiceImpl;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class SignController {

    private final SignServiceImpl signService;

    public SignController(SignServiceImpl signService) {
        this.signService = signService;
    }

    @PostMapping(value = "/sign-in")
    public SignInResultDTO signIn(
            @ApiParam(value = "ID", required = true) @RequestParam String id,
            @ApiParam(value = "Password", required = true) @RequestParam String password)
            throws RuntimeException {

        // 엥 왜 오류가 날까
        // signIn에서 signInResultDTO를 리턴하는데..?
        // 서비스 인터페이스 타입을 잘못 설정해 준 거였음 ㅡ ㅡ
        SignInResultDTO signInResultDTO = signService.signIn(id, password);

        if (signInResultDTO.getCode() == 0) {
            System.out.println("test");
            signInResultDTO.getToken();
        }
        return signInResultDTO;
    }

    @PostMapping(value = "/sign-up")
    public SignUpResultDTO signUp(
            @ApiParam(value = "ID", required = true) @RequestParam String id,
            @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
            @ApiParam(value = "이름", required = true) @RequestParam String name,
            @ApiParam(value = "권한", required = true) @RequestParam String role) {
        SignUpResultDTO signUpResultDTO = signService.signUp(id, password, name, role);

        return signUpResultDTO;
    }

    @GetMapping(value = "/exception")
    public void exceptionTest() throws RuntimeException {
        throw new RuntimeException("접근이 금지되었습니다.");
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(RuntimeException e) {
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        Map<String, String> map = new HashMap<>();

        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", "에러 발생");
        map.put("내용", e.getMessage());

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }



}
