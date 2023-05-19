package com.example.todolist.dto;

import com.example.todolist.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class MemberReqDto {

    private String name;

    private String nickName;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @Builder
    public MemberReqDto(String name, String nickName, String email) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
    }

    public Member toMember(MemberReqDto memberReqDto) {
        return Member.builder()
                .name(memberReqDto.getName())
                .nickName(memberReqDto.getNickName())
                .email(memberReqDto.getEmail())
                .build();
    }

}
