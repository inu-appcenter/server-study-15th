package com.appcenter.data.dto.response;

import com.appcenter.data.entity.Todolist;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodolistResponseDTO {
    private Long id;
    private String title;
    private String contents;
    private Long member_id;

    @Builder
    public TodolistResponseDTO(Long id, String title, String contents, Long member_id) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.member_id = member_id;
    }

    public TodolistResponseDTO createTodolistResponse(Todolist todolist) {
        return TodolistResponseDTO.builder()
                .id(todolist.getId())
                .title(todolist.getTitle())
                .contents(todolist.getContents())
                .member_id(todolist.getMember().getId())
                .build();
    }

    public TodolistResponseDTO updateTodolistResponse(Todolist todolist) {
            return TodolistResponseDTO.builder()
                    .id(todolist.getId())
                    .title(todolist.getTitle())
                    .contents(todolist.getContents())
                    .member_id(todolist.getMember().getId())
                    .build();
    }
}
