package com.example.todolist.controller;


import com.example.todolist.domain.Member;
import com.example.todolist.domain.Message;
import com.example.todolist.dto.MemberPageRespDto;
import com.example.todolist.dto.MemberRespDto;
import com.example.todolist.dto.UpdateMemberReqDto;
import com.example.todolist.service.MemberService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private static final String SUCCESS_MEMBER_UPDATE_MESSAGE = "회원정보가 수정되었습니다.";
    private static final String SUCCESS_MEMBER_DELETE_MESSAGE = "회원정보가 삭제되었습니다.";

    @ApiOperation(value = "유저정보 수정", notes = "모든에게 권한 부여")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "accessToken", required = true, dataType = "String", paramType = "header")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMember(@Valid @RequestBody UpdateMemberReqDto updateMemberReqDto) {
        memberService.update(getMember().getId(), updateMemberReqDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new Message(SUCCESS_MEMBER_UPDATE_MESSAGE));
    }

    @ApiOperation(value = "유저정보 삭제", notes = "관리자만 권한 부여")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "accessToken", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Message(SUCCESS_MEMBER_DELETE_MESSAGE));
    }

    @ApiOperation(value = "전체 유저 조회", notes = "관리자만 권한 부여")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "accessToken", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping()
    public ResponseEntity<List<MemberPageRespDto>> readAllMember() {
        List<MemberPageRespDto> todos = memberService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(todos);
    }

    @ApiOperation(value = "개인정보 조회", notes = "모두에게 권한 부여")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "accessToken", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MemberRespDto> readOneMember() {
        MemberRespDto member = memberService.findOne(getMember().getId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(member);
    }

    private static Member getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Member) authentication.getPrincipal();
    }
}
