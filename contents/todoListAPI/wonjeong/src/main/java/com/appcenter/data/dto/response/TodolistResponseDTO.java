package com.appcenter.data.dto.response;

import com.appcenter.data.entity.Todolist;
import lombok.Builder;
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

    @Builder
    public TodolistResponseDTO(Long id, String title, String contents, Long member_id, LocalDateTime lastModifiedDate, LocalDateTime createDate) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.member_id = member_id;
        this.lastModifiedDate = lastModifiedDate;
        this.createDate = createDate;
    }

    public TodolistResponseDTO createTodolistResponse(Todolist todolist) {
        return TodolistResponseDTO.builder()
                .id(todolist.getId())
                .title(todolist.getTitle())
                .contents(todolist.getContents())
                .member_id(todolist.getMember().getId())
                .createDate(todolist.getCreatedDate())
                .lastModifiedDate(todolist.getLastModifiedDate())
                .build();
    }

    public TodolistResponseDTO updateTodolistResponse(Todolist todolist) {
            return TodolistResponseDTO.builder()
                    .id(todolist.getId())
                    .title(todolist.getTitle())
                    .contents(todolist.getContents())
                    .member_id(todolist.getMember().getId())
                    .createDate(todolist.getCreatedDate())
                    .lastModifiedDate(todolist.getLastModifiedDate())
                    .build();
    }
}
