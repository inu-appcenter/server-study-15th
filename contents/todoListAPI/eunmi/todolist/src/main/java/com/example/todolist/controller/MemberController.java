package com.example.todolist.controller;


import com.example.todolist.domain.Member;
import com.example.todolist.dto.MemberDto;
import com.example.todolist.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping()
    public String join(@RequestBody MemberDto memberDto) {
        Long save = memberService.save(memberDto);
        return save + "번 멤버 생성 완료";
    }

    @PutMapping("/{id}")
    public String updateMember(@PathVariable Long id, @RequestBody MemberDto memberDto) {
        Long update = memberService.update(id, memberDto);
        return update + "번 멤버 수정 완료";
    }

    @DeleteMapping("/{id}")
    public String deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return id + "번 멤버 삭제";
    }

    @GetMapping()
    public List<Member> readAllMember() {
        return memberService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> readOneMember(@PathVariable Long id) {
        MemberDto member = memberService.findOne(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(member);
    }
}
