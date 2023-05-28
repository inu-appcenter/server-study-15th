package com.example.todo.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class SignInRequest {

    @Schema(description = "유저 이메일", example = "xxx@gmail.com")
    @NotBlank
    @Email
    private String email;

    @Schema(description = "유저 비밀번호", example = "asd123")
    @NotBlank
    private String password;

    public SignInRequest(String email, String password){
        this.email = email;
        this.password = password;
    }
}
