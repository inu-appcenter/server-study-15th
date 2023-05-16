package com.appcenter.controller;

import com.appcenter.data.dto.MemberDTO;
import com.appcenter.data.dto.response.MemberResponseDTO;
import com.appcenter.data.dto.request.ChangeMemberDTO;
import com.appcenter.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {

    // MemberService를 상수로 선언
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping()
    public ResponseEntity<MemberResponseDTO> getMember(Long number) {
        MemberResponseDTO memberResponseDTO = memberService.getMember(number);

        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDTO);
    }

    @PostMapping()
    public ResponseEntity<MemberResponseDTO> createMember(@RequestBody MemberDTO memberDTO) {
        MemberResponseDTO memberResponseDTO = memberService.savedMember(memberDTO);

        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDTO);
    }

    @PutMapping()
    public ResponseEntity<MemberResponseDTO> changeMemberInfo(@RequestBody ChangeMemberDTO changeMemberDTO) throws Exception {
        MemberResponseDTO memberResponseDTO = memberService.changeMemberinfo(
                changeMemberDTO.getNumber(),
                changeMemberDTO.getName()
        );

        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDTO);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteMember(Long number) throws Exception{
        memberService.deleteMember(number);

        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }
}
