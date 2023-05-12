package com.example.todolist.dto;

import com.example.todolist.domain.Member;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDto {

    private String name;

    private String nickName;

    private String email;

    @Builder
    public MemberDto(String name, String nickName, String email) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
    }

    public Member toMember(MemberDto memberDto) {
        return Member.builder()
                .name(memberDto.getName())
                .nickName(memberDto.getNickName())
                .email(memberDto.getEmail())
                .build();
    }

}
