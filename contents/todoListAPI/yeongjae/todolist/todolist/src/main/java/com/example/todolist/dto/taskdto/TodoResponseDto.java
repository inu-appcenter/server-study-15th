package com.example.todolist.dto.taskdto;

import com.example.todolist.domain.Todo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


public class TodoResponseDto {

    @Getter
    @Setter
    public static class TaskSaveRespDto {
        @Schema(example = "Todo 제목")
        @NotEmpty
        private String title;

        @Schema(example = "Todo 내용")
        private String contents;
        @NotEmpty
        @Schema(example = "Todo 기한")
        private String deadline;

        private boolean isCompleted;

        public TaskSaveRespDto(Todo todo) {
            this.title = todo.getTitle();
            this.contents = todo.getContents();
            this.deadline = todo.getDeadline().toString();
            this.isCompleted = todo.getIsCompleted();
        }
    }

    @Getter
    @Setter
    public static class TaskDeleteRespDto {
        @Schema(example = "Todo 제목")
        @NotEmpty
        private String title;

        @Schema(example = "Todo 내용")
        private String contents;
        @NotEmpty
        @Schema(example = "Todo 기한")
        private String deadline;

        private boolean isCompleted;

        public TaskDeleteRespDto(Todo todo) {
            this.title = todo.getTitle();
            this.contents = todo.getContents();
            this.deadline = todo.getDeadline().toString();
            this.isCompleted = todo.getIsCompleted();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class TaskEditRespDto {
        private String title;
        private String contents;
        private String deadline;
        private boolean isCompleted;

        public TaskEditRespDto(Todo todo) {
            this.title = todo.getTitle();
            this.contents = todo.getContents();
            this.deadline = todo.getDeadline().toString();
            this.isCompleted = todo.getIsCompleted();
        }
    }
}
