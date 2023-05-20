package com.example.todolist.dto;

import com.example.todolist.domain.Member;
import com.example.todolist.domain.Todo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoReqDto {

    private String content;

    private LocalDateTime creatAt;

    private boolean checked;

    @Builder
    public TodoReqDto(String content, LocalDateTime creatAt, boolean checked) {
        this.content = content;
        this.creatAt = creatAt;
        this.checked = checked;
    }

    public Todo toEntity(Member member, TodoReqDto todoReqDto) {
        return Todo.builder()
                .member(member)
                .content(todoReqDto.getContent())
                .creatAt(todoReqDto.getCreatAt())
                .checked(false)
                .build();
    }


}
