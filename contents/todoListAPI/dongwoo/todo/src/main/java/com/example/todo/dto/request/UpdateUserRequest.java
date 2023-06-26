package com.example.todo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.Email;

@Getter
public class UpdateUserRequest {

    @Schema(description = "유저 이름", example = "김동우")
    private String name;

    @Schema(description = "유저 이메일", example = "xxx@gamil.com")
    @Email
    private String email;

}
