package com.example.todolist.dto.taskdto;

import com.example.todolist.domain.Todo;
import com.example.todolist.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class TodoRequestDto {

    public static final int MIN_TITLE_VALUE = 1;
    public static final int MAX_TITLE_VALUE = 30;



    @Getter
    @Setter
    public static class TodoSaveReqDto {
        @NotEmpty
        @Size(min = MIN_TITLE_VALUE, max = MAX_TITLE_VALUE)
        private String title;

        private String contents;
        @NotEmpty
        @FutureOrPresent
        private String deadline;

        @NotEmpty
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

        @NotEmpty
        @Size(min = MIN_TITLE_VALUE, max = MAX_TITLE_VALUE)
        @Schema(description = "Todo 제목")
        private String title;
        @Schema(description = "Todo 내용")
        private String contents;
        @Schema(description = "Todo 기한")
        @NotEmpty
        @FutureOrPresent
        private String deadline;
        @Schema(description = "Todo 실행 여부")
        @NotEmpty
        private boolean isCompleted;
    }

    public static LocalDateTime parseDatetime(String deadline) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(deadline, dateTimeFormatter).atStartOfDay();
    }
}