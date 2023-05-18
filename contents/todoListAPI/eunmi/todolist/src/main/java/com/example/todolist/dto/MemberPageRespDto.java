package com.example.todolist.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MemberPageRespDto {
    private String name;
    private String nickName;
    private String email;
}
