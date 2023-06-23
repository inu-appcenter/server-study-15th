package com.example.todolist.dto.taskdto;

import com.example.todolist.domain.Todo;
import com.example.todolist.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class TodoRequestDto {

    @Getter
    @Setter
    public static class TodoSaveReqDto {
        @NotEmpty
        private String title;

        private String contents;
        @NotEmpty
        private String deadline;

        private boolean isCompleted;

        public Todo changeEntity(TodoSaveReqDto TodoSaveReqDto, User user) {
            return Todo.builder()
                    .title(TodoSaveReqDto.title)
                    .contents(TodoSaveReqDto.contents)
                    .user(user)
                    .deadline(parseDatetime(TodoSaveReqDto.deadline))
                    .isCompleted(TodoSaveReqDto.isCompleted)
                    .build();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class TodoEditRequestDto {
        @Schema(description = "Todo 제목")
        private String title;
        @Schema(description = "Todo 내용")
        private String contents;
        @Schema(description = "Todo 기한")
        private String deadline;
        @Schema(description = "Todo 실행 여부")
        private boolean isCompleted;
    }

    public static LocalDateTime parseDatetime(String deadline) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(deadline, dateTimeFormatter).atStartOfDay();
    }
}