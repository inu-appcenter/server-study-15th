package appCenter.guCoding.todoList.controller;


import appCenter.guCoding.todoList.dto.ResponseDto;
import appCenter.guCoding.todoList.dto.user.UserReqDto;
import appCenter.guCoding.todoList.dto.user.UserRespDto;
import appCenter.guCoding.todoList.dto.user.UserRespDto.JoinRespDto;
import appCenter.guCoding.todoList.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static appCenter.guCoding.todoList.dto.user.UserReqDto.*;
import static appCenter.guCoding.todoList.dto.user.UserRespDto.*;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid JoinReqDto joinReqDto, BindingResult bindingResult) {
        JoinRespDto joinRespDto = userService.회원가입(joinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1,"회원가입 완료", joinRespDto), HttpStatus.CREATED);

    }

    @PutMapping("/")
    public ResponseEntity<?> editUser(@RequestBody @Valid UserEditReqDto userEditReqDto, BindingResult bindingResult, String username) {
        UserEditRespDto userEditRespDto = userService.사용자정보수정(userEditReqDto, username);
        return new ResponseEntity<>(new ResponseDto<>(1, "사용자 정보 수정 성공", userEditRespDto), HttpStatus.OK);
    }
}
