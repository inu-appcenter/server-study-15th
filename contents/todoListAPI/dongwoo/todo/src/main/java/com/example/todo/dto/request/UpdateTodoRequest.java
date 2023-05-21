package com.example.todo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
public class UpdateTodoRequest {

    @Schema(description = "수정할 todo 내용", example = "수정할 todo 입니다")
    @NotBlank
    private String contents;

}
