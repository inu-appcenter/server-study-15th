package com.example.todolist.controller;


import com.example.todolist.domain.Message;
import com.example.todolist.dto.MemberReqDto;
import com.example.todolist.dto.MemberPageRespDto;
import com.example.todolist.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping()
    public ResponseEntity<Message> join(@Valid @RequestBody MemberReqDto memberReqDto) {
        Message message = memberService.save(memberReqDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(message);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMember(@PathVariable Long id, @Valid @RequestBody MemberReqDto memberReqDto) {
        Long update = memberService.update(id, memberReqDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Message(SUCCESS_MEMBER_UPDATE_MESSAGE));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Message(SUCCESS_MEMBER_DELETE_MESSAGE));
    }

    @GetMapping()
    public ResponseEntity<List<MemberPageRespDto>> readAllMember() {
        List<MemberPageRespDto> todos = memberService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(todos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberReqDto> readOneMember(@PathVariable Long id) {
        MemberReqDto member = memberService.findOne(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(member);
    }
}
