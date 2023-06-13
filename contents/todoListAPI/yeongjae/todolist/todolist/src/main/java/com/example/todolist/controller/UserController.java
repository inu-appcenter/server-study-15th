package com.example.todolist.controller;

import com.example.todolist.dto.ResponseDto;
import com.example.todolist.dto.userdto.UserRequestDto.UserJoinReqDto;
import com.example.todolist.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.todolist.dto.userdto.UserRequestDto.*;
import static com.example.todolist.dto.userdto.UserResponseDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @Operation(summary = "회원가입", description = "바디에 {username, password, email} 을 json 형식으로 보내주세요")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "동일한 사용자명이 존재합니다."),
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 201,message = "ok,",response = UserJoinReqDto.class)
    )
    @PostMapping("/join")
    public ResponseEntity<?> joinUser(@RequestBody UserJoinReqDto userJoinReqDto) {
        UserJoinRespDto userJoinRespDto = userService.join(userJoinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1,"회원 가입 성공",userJoinRespDto), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @ApiResponse(responseCode = "200", description = "정보조회 성공")
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

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginReqDto userLoginReqDto) {
        return new ResponseEntity<>(new ResponseDto<>(1, "유저 로그인", userLoginReqDto), HttpStatus.OK);
    }


}