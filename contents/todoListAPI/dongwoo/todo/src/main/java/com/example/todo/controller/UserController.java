package com.example.todo.controller;

import com.example.todo.common.dto.CommonResponse;
import com.example.todo.dto.data.UserId;
import com.example.todo.dto.request.GeneralSignUpRequest;
import com.example.todo.dto.request.SignInRequest;
import com.example.todo.dto.request.UpdateUserRequest;
import com.example.todo.dto.response.JwtLoginResponse;
import com.example.todo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "user", description = "user api 입니다")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "바디에 {name, email, password} 을 json 형식으로 보내주시면 됩니다. " +
            "email 은 꼭 email 형식으로 보내주셔야 합니다")
    @PostMapping("/signup")
    public ResponseEntity<CommonResponse> registerUser(@Valid @RequestBody GeneralSignUpRequest request) {
        userService.registerUser(request.toDomain());

        CommonResponse response = new CommonResponse("회원가입에 성공했습니다.");

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @Operation(summary = "로그인", description = "바디에 {email, password} 를 json 형식으로 보내주시면 됩니다. " +
        "email 은 꼭 email 형식으로 보내주셔야 합니다")
    @PostMapping("/login")
    public ResponseEntity<JwtLoginResponse> login(@RequestBody SignInRequest request) {
        return new ResponseEntity<>(userService.login(request), HttpStatus.OK);
    }

    @Operation(summary = "회원 수정", description = "바디에 {name, email} 를 json 형식으로 보내주시고 jwt 토큰 bearer 헤더에" +
            "보내주시면 됩니다")
    @PutMapping("")
    public ResponseEntity<CommonResponse> updateUser(@RequestBody UpdateUserRequest request, UserId userId) {

        userService.updateUser(request, userId.getId());
        CommonResponse response = new CommonResponse("회원 수정 완료 했습니다");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
