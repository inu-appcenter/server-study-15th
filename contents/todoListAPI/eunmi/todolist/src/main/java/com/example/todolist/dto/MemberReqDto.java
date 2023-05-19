package com.example.todolist.dto;

import com.example.todolist.domain.Member;
import lombok.*;

import javax.validation.constraints.Email;

@Getter
@NoArgsConstructor
public class MemberReqDto {

    private String name;

    private String nickName;

    @Email
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
