package com.example.todo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CreateTodoRequest {

    @Schema(description = "todo 의 내용", example = "운동")
    @NotBlank
    private String contents;

    @Schema(description = "todo 의 기한")
    private Date deadLine;

}
