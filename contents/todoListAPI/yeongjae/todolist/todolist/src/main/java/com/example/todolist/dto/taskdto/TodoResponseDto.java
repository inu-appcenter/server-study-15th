package com.example.todolist.dto.taskdto;

import com.example.todolist.domain.Todo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
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
        @NotEmpty
        @Size(min = MIN_TITLE_VALUE, max = MAX_TITLE_VALUE)
        private String title;

        private String contents;

        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "잘못된 기한일 형식입니다.")
        private String deadline;

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
        @NotEmpty
        @Size(min = MIN_TITLE_VALUE, max = MAX_TITLE_VALUE)
        private String title;

        private String contents;
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "잘못된 기한일 형식입니다.")
        private String deadline;
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

        @NotEmpty
        @Size(min = MIN_TITLE_VALUE, max = MAX_TITLE_VALUE)
        private String title;

        private String contents;

        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "잘못된 기한일 형식입니다.")
        private String deadline;
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
