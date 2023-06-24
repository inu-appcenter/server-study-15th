package com.example.todolist.dto.taskdto;

import com.example.todolist.domain.Todo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Schema
public class TodoResponseDto {
    public static final int MIN_TITLE_VALUE = 1;
    public static final int MAX_TITLE_VALUE = 30;


    @Getter
    @Setter
    @Schema(description = "Todo 저장 응답 Dto")
    public static class TodoSaveRespDto {
        @Schema(description = "Todo 제목")
        @NotEmpty
        @Size(min = MIN_TITLE_VALUE, max = MAX_TITLE_VALUE)
        private String title;

        @Schema(description = "Todo 내용")
        private String contents;

        @NotEmpty
        @Schema(description = "Todo 기한")
        private String deadline;

        @Schema(description = "Todo 실행 여부")
        @NotEmpty
        @FutureOrPresent
        private boolean isCompleted;

        public TodoSaveRespDto(Todo todo) {
            this.title = todo.getTitle();
            this.contents = todo.getContents();
            this.deadline = todo.getDeadline().toString();
            this.isCompleted = todo.getIsCompleted();
        }
    }

    @Getter
    @Setter
    @Schema(description = "Todo 삭제 응답 Dto")
    public static class TodoDeleteRespDto {
        @Schema(example = "Todo 제목")
        @NotEmpty
        @Size(min = MIN_TITLE_VALUE, max = MAX_TITLE_VALUE)
        private String title;

        @Schema(example = "Todo 내용")
        private String contents;
        @NotEmpty
        @Schema(example = "Todo 기한")
        @FutureOrPresent
        private String deadline;
        @Schema(description = "Todo 실행 여부")
        @NotEmpty
        private boolean isCompleted;

        public TodoDeleteRespDto(Todo todo) {
            this.title = todo.getTitle();
            this.contents = todo.getContents();
            this.deadline = todo.getDeadline().toString();
            this.isCompleted = todo.getIsCompleted();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Schema(description = "Todo 수정 응답 Dto")
    public static class TodoEditRespDto {

        @Schema(example = "Todo 제목")
        @NotEmpty
        @Size(min = MIN_TITLE_VALUE, max = MAX_TITLE_VALUE)
        private String title;
        @Schema(example = "Todo 내용")
        private String contents;
        @NotEmpty
        @Schema(example = "Todo 기한")
        @FutureOrPresent
        private String deadline;
        @Schema(description = "Todo 실행 여부")
        @NotEmpty
        private boolean isCompleted;

        public TodoEditRespDto(Todo todo) {
            this.title = todo.getTitle();
            this.contents = todo.getContents();
            this.deadline = todo.getDeadline().toString();
            this.isCompleted = todo.getIsCompleted();
        }
    }

    @Getter
    @Setter
    @Schema
    public static class TodoListRespDto {
        private List<TodoOneDto> todos;

        public TodoListRespDto(List<Todo> todos) {
            this.todos = todos.stream()
                    .map(TodoOneDto::new)
                    .collect(Collectors.toList());
        }

        @Getter
        @Setter
        public static class TodoOneDto {
            private Long id;
            private String title;
            private String contents;
            private Boolean isCompleted;
            private LocalDateTime deadline;

            public TodoOneDto(Todo todo) {
                this.id = todo.getId();
                this.title = todo.getTitle();
                this.contents = todo.getContents();
                this.isCompleted = todo.getIsCompleted();
                this.deadline = todo.getDeadline();
            }
        }
    }
}
