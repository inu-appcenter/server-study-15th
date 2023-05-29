package appCenter.guCoding.todoList.controller;


import appCenter.guCoding.todoList.config.auth.LoginUser;
import appCenter.guCoding.todoList.domain.task.Task;
import appCenter.guCoding.todoList.dto.ResponseDto;
import appCenter.guCoding.todoList.dto.user.UserRespDto;
import appCenter.guCoding.todoList.dto.user.UserRespDto.JoinRespDto;
import appCenter.guCoding.todoList.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag; // 오류있는 것 같은데
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @Operation(summary = "회원가입", description = "바디에 {username, password, email(이메일형식)} 을 json 형식으로 보내주세요")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = JoinRespDto.class))),
            @ApiResponse(responseCode = "400", description = "동일한 사용자명이 존재합니다."),
            @ApiResponse(responseCode = "400", description = "유효성검사 실패"),
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 201,message = "ok,",response = JoinRespDto.class)
    )
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid JoinReqDto joinReqDto, BindingResult bindingResult) {
        JoinRespDto joinRespDto = userService.회원가입(joinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1,"회원가입 완료", joinRespDto), HttpStatus.CREATED);

    }

    @Operation(summary = "사용자 정보 수정", description = "바디에 {username, password, email(이메일형식)} 을 json 형식으로 보내주세요")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용자 정보 수정 성공", content = @Content(schema = @Schema(implementation = UserEditRespDto.class))),
            @ApiResponse(responseCode = "400", description = "유효성검사 실패"),
            @ApiResponse(responseCode = "404", description = "해당 id 의 사용자가 없습니다.")
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 200,message = "ok,",response = UserEditRespDto.class)
    )
    @PutMapping
    public ResponseEntity<?> editUser(@RequestBody @Valid UserEditReqDto userEditReqDto, BindingResult bindingResult, @AuthenticationPrincipal LoginUser loginUser) {
        UserEditRespDto userEditRespDto = userService.사용자정보수정(userEditReqDto, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "사용자 정보 수정 성공", userEditRespDto), HttpStatus.OK);
    }

    @Operation(summary = "사용자 조회", description = "관리자만 볼 수 있습니다.")
    @ApiResponse(responseCode = "200", description = "사용자 조회 성공", content = @Content(schema = @Schema(implementation = UserListRespDto.class)))
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 200,message = "ok,",response = UserListRespDto.class)
    )
    @GetMapping("/admin")
    public ResponseEntity<?> checkUser() {
        UserListRespDto userListRespDto = userService.사용자목록보기();
        return new ResponseEntity<>(new ResponseDto<>(1, "사용자 조회 성공", userListRespDto), HttpStatus.OK);
    }

    @Operation(summary = "사용자 삭제", description = "경로변수에 id 값을 보내주세요")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "사용자 삭제 성공", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "해당 id 의 사용자가 없습니다.")
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 204,message = "ok,",response = ResponseDto.class) // 어떻게 들어오나 확인
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, @AuthenticationPrincipal LoginUser loginUser) {
        userService.사용자삭제(id, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "사용자 삭제 성공", null), HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "로그인", description = "바디에 {username, password} 을 json 형식으로 보내주세요")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공" , content = @Content(schema = @Schema(implementation = LoginRespDto.class))),
            @ApiResponse(responseCode = "401", description = "로그인 실패")
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 200,message = "ok,",response = LoginRespDto.class)
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReqDto loginReqDto) {
        return null;
    }

    @Operation(summary = "본인 정보 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "본인 정보 확인 성공", content = @Content(schema = @Schema(implementation = UserMyInfoRespDto.class))),
            @ApiResponse(responseCode = "404", description = "본인 정보 확인 실패")
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 200,message = "ok,",response = UserMyInfoRespDto.class)
    )
    @GetMapping("/info")
    public ResponseEntity<?> checkMyInfo(@AuthenticationPrincipal LoginUser loginUser) {
        UserMyInfoRespDto userMyInfoRespDto = userService.본인정보확인(loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "본인 정보 확인 성공", userMyInfoRespDto), HttpStatus.OK);

    }
}
