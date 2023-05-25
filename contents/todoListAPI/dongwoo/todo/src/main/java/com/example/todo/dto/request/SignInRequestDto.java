package com.example.todo.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Getter
@NoArgsConstructor
public class SignInRequestDto {

    @Email
    private String email;

    private String password;

    public SignInRequestDto(String email, String password){
        this.email = email;
        this.password = password;
    }
}
