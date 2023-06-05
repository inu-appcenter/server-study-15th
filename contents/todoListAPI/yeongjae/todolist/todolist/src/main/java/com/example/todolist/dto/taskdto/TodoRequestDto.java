package com.example.todolist.dto.taskdto;

import com.example.todolist.domain.Todo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Schema(description = "TodoRequest")
public class TodoRequestDto {

    @Getter
    @Setter
    public static class TodoSaveReqDto {
        @Schema(example = "Todo 제목")
        @NotEmpty
        private String title;

        @Schema(example = "Todo 내용")
        private String contents;
        @NotEmpty
        @Schema(example = "Todo 기한")
        private String deadline;

        private boolean isCompleted;

        public Todo changeEntity(TodoSaveReqDto TodoSaveReqDto) {
            return Todo.builder()
                    .title(TodoSaveReqDto.title)
                    .contents(TodoSaveReqDto.contents)
                    .deadline(parseDatetime(TodoSaveReqDto.deadline))
                    .isCompleted(TodoSaveReqDto.isCompleted)
                    .build();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class TodoEditRequestDto {
        private String title;
        private String contents;
        private String deadline;
        private boolean isCompleted;
    }

    public static LocalDateTime parseDatetime(String deadline) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(deadline, dateTimeFormatter).atStartOfDay();
    }
}