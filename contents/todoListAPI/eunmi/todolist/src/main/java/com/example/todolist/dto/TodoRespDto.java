package com.example.todolist.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class TodoRespDto {

    private Long id;

    private String content;

    private LocalDateTime creatAt;

    private boolean checked;

    @Builder
    public TodoRespDto(Long id, String content, LocalDateTime creatAt, boolean checked) {
        this.id = id;
        this.content = content;
        this.creatAt = creatAt;
        this.checked = checked;
    }
}
