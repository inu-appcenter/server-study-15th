package com.appcenter.data.dto.response;

import com.appcenter.data.entity.Todolist;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class TodolistResponseDTO {
    private Long id;
    private String title;
    private String contents;

    @Builder
    public TodolistResponseDTO(Long id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    public TodolistResponseDTO updateTodolistResponse(Todolist todolist) {
            return TodolistResponseDTO.builder()
                    .id(todolist.getId())
                    .title(todolist.getTitle())
                    .contents(todolist.getContents())
                    .build();
    }
}
