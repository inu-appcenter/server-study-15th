package com.example.todolist.controller;

import com.example.todolist.dto.ResponseDto;
import com.example.todolist.dto.userdto.UserRequestDto.UserJoinReqDto;
import com.example.todolist.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.todolist.dto.userdto.UserResponseDto.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<?> joinUser(@RequestBody UserJoinReqDto userJoinReqDto) {
        UserJoinRespDto userJoinRespDto = memberService.join(userJoinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1,"회원가입 성공",userJoinRespDto), HttpStatus.CREATED);
    }
}