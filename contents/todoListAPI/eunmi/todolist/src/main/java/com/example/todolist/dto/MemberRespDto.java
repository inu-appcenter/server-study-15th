package com.example.todolist.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberRespDto {
    private String userName;
    private String name;
    private String email;
    private String role;

    @Builder
    public MemberRespDto(String userName, String name, String email, String role) {
        this.userName = userName;
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
