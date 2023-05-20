package com.example.todolist.dto.taskdto;

import com.example.todolist.domain.Task;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


public class TaskResponseDto {

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

        public TaskSaveRespDto(Task task) {
            this.title = task.getTitle();
            this.contents = task.getContents();
            this.deadline = task.getDeadline().toString();
            this.isCompleted = task.getIsCompleted();
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

        public TaskDeleteRespDto(Task task) {
            this.title = task.getTitle();
            this.contents = task.getContents();
            this.deadline = task.getDeadline().toString();
            this.isCompleted = task.getIsCompleted();
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

        public TaskEditRespDto(Task task) {
            this.title = task.getTitle();
            this.contents = task.getContents();
            this.deadline = task.getDeadline().toString();
            this.isCompleted = task.getIsCompleted();
        }
    }
}
