package com.example.todolist.dto;

import com.example.todolist.domain.Member;
import com.example.todolist.domain.Todo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TodoReqDto {

    private String content;

    private boolean checked;

    @Builder
    public TodoReqDto(String content, boolean checked) {
        this.content = content;
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
