package com.example.todolist.dto;

import com.example.todolist.domain.Member;
import com.example.todolist.domain.Todo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoReqDto {

    private String content;

    private LocalDateTime deadline;

    private boolean checked;

    @Builder
    public TodoReqDto(String content, LocalDateTime deadline, boolean checked) {
        this.content = content;
        this.deadline = deadline;
        this.checked = checked;
    }

    public Todo toEntity(Member member, TodoReqDto todoReqDto) {
        return Todo.builder()
                .member(member)
                .content(todoReqDto.getContent())
                .checked(false)
                .build();
    }


}
