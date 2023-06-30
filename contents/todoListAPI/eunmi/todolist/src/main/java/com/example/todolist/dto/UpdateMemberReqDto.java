package com.example.todolist.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateMemberReqDto {

    private String email;

    @Builder
    public UpdateMemberReqDto(String password) {
        this.email = email;
    }
}
