package com.appcenter.controller;

import com.appcenter.data.dto.request.MemberRequestDTO;
import com.appcenter.data.dto.response.MemberResponseDTO;
import com.appcenter.service.MemberService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "발급 받은 Token",
                    required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> getMember(@PathVariable Long id) throws Exception{
        MemberResponseDTO memberResponseDTO = memberService.getMember(id);

        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDTO);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 Token",
                    required = true, dataType = "String", paramType = "header")
    })
    @PostMapping()
    public ResponseEntity<MemberResponseDTO> createMember(@RequestBody MemberRequestDTO memberRequestDTO) {
        MemberResponseDTO memberResponseDTO = memberService.savedMember(memberRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(memberResponseDTO);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "발급 받은 Token",
                    required = true, dataType = "String", paramType = "header")
    })
    @PutMapping()
    public ResponseEntity<MemberResponseDTO> updateMember(@RequestBody MemberRequestDTO memberRequestDTO, Long id) throws Exception {
        MemberResponseDTO memberResponseDTO = memberService.updateMember(id, memberRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDTO);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "발급 받은 Token",
                    required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping()
    public ResponseEntity<String> deleteMember(Long id) throws Exception{
        memberService.deleteMember(id);

        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 회원이 삭제되었습니다.");
    }
}
