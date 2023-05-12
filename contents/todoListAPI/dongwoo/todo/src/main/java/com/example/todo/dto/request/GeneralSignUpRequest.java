package com.example.todo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class GeneralSignUpRequest {

    @Schema(description = "유저 이름", example = "홀길동")
    @NotBlank
    private String name;

    @Schema(description = "유저 이메일", example = "xxx@gmail.com")
    @Email
    @NotBlank
    private String email;

    @Schema(description = "유저 비밀번호", example = "asd123")
    @NotBlank
    private String password;

    public GeneralSignUpInfo toDomain() {
        return new GeneralSignUpInfo(name, email, password);
    }

}
