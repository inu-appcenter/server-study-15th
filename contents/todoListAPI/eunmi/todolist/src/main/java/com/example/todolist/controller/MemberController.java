package com.example.todolist.controller;


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
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping()
    public ResponseEntity<String> join(@Valid @RequestBody MemberReqDto memberReqDto) {
        Long save = memberService.save(memberReqDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("회원정보가 생성 되었습니다.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateMember(@PathVariable Long id, @Valid @RequestBody MemberReqDto memberReqDto) {
        Long update = memberService.update(id, memberReqDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body("회원정보가 수정되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("회원정보가 삭제 되었습니다.");
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
