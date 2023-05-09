package com.example.todo.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CreateTodoRequest {

    @NotBlank
    private String contents;

    private Date deadLine;

}
