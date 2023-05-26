package com.example.todolist.controller;

import com.example.todolist.dto.ResponseDto;
import com.example.todolist.dto.userdto.UserRequestDto.UserJoinReqDto;
import com.example.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.example.todolist.dto.userdto.UserRequestDto.*;
import static com.example.todolist.dto.userdto.UserResponseDto.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> joinUser(@RequestBody UserJoinReqDto userJoinReqDto) {
        UserJoinRespDto userJoinRespDto = userService.join(userJoinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1,"회원 가입 성공",userJoinRespDto), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAllUser() {
        return new ResponseEntity<>(new ResponseDto<>(1, "유저 정보 반환", userService.findAll()), HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editUser(@PathVariable Long id, @RequestBody UserEditReqDto userEditReqDto) {
        return new ResponseEntity<>(new ResponseDto<>(1, "회원 수정 성공", userService.editUser(id, userEditReqDto)), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return new ResponseEntity<>(new ResponseDto<>(1, "유저 삭제", userService.deleteUser(id)), HttpStatus.OK);
    }

    /*
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginReqDto userLoginReqDto) {

    }
    */
}