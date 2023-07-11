package com.appcenter.data.dto.response;

import com.appcenter.data.entity.Todolist;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TodolistResponseDTO {
    private Long id;
    private String title;
    private String contents;
    private Long member_id;
    private LocalDateTime lastModifiedDate;
    private LocalDateTime createDate;

    public void setTodolistResponse(Todolist todolist) {
        this.id = todolist.getId();
        this.title = todolist.getTitle();
        this.contents = todolist.getContents();
        this.member_id = todolist.getMember().getId();
        this.createDate = todolist.getCreatedDate();
        this.lastModifiedDate = todolist.getLastModifiedDate();
    }
}