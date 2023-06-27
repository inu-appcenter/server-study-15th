package com.example.todolist.controller;

import com.example.todolist.dto.ResponseDto;
import com.example.todolist.dto.userdto.UserRequestDto.UserJoinReqDto;
import com.example.todolist.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.todolist.dto.userdto.UserRequestDto.*;
import static com.example.todolist.dto.userdto.UserResponseDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    @Operation(summary = "회원가입", description = "바디에 {username, password, email, roles} 을 json 형식으로 보내주세요")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "회원 가입 성공"),
            @ApiResponse(responseCode = "409", description = "동일한 아이디를 가진 회원이 있습니다")})
    public ResponseEntity<?> joinUser(@RequestBody @Valid final UserJoinReqDto userJoinReqDto) {
        UserJoinRespDto userJoinRespDto = userService.join(userJoinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1,"회원 가입 성공",userJoinRespDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "유저 정보 조회", description = "파라미터에 식별자 정보를 보내주세요")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "유저 정보 조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 식별자를 가진 회원을 찾을 수 없습니다.")})
    public ResponseEntity<?> findUser(@PathVariable Long id, UserId userId) {
        return new ResponseEntity<>(new ResponseDto<>(1, "유저 정보 반환", userService.findUser(id, userId)),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "유저 정보 수정", description = "바디에 {username, password, email} 을 json 형식으로 보내주세요")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "유저 정보 수정 성공"),
            @ApiResponse(responseCode = "404", description = "해당 식별자를 가진 회원을 찾을 수 없습니다.")})
    public ResponseEntity<?> editUser(@PathVariable Long id, @RequestBody @Valid final UserEditReqDto userEditReqDto, UserId userId) {
        return new ResponseEntity<>(new ResponseDto<>(1, "회원 수정 성공", userService.editUser(id, userEditReqDto, userId)), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "유저 삭제", description = "파라미터에 식별자 정보를 보내주세요")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "유저 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "해당 식별자를 가진 회원을 찾을 수 없습니다.")})
    public ResponseEntity<?> deleteUser(@PathVariable Long id, UserId userId) {
        return new ResponseEntity<>(new ResponseDto<>(1, "유저 삭제", userService.deleteUser(id, userId)), HttpStatus.OK);
    }

    @GetMapping("/all")
    @Operation(summary = "전체 유저 정보 조회", description = "ADMIN 권한만 가능합니다")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "유저 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "해당 식별자를 가진 회원을 찾을 수 없습니다.")})
    @ApiResponse(responseCode = "200", description = "전체 유저 정보 조회 성공")
    public ResponseEntity<?> findAllUser() {
        return new ResponseEntity<>(new ResponseDto<>(1, "전체 유저 정보 반환", userService.findAll()), HttpStatus.OK);
    }
}