package com.example.todolist.dto.taskdto;

import com.example.todolist.domain.Task;
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
public class TaskRequestDto {

    @Getter
    @Setter
    public static class TaskSaveReqDto {
        @Schema(example = "Todo 제목")
        @NotEmpty
        private String title;

        @Schema(example = "Todo 내용")
        private String contents;
        @NotEmpty
        @Schema(example = "Todo 기한")
        private String deadline;

        private boolean isCompleted;

        public Task changeEntity(TaskSaveReqDto TaskSaveReqDto) {
            return Task.builder()
                    .title(TaskSaveReqDto.title)
                    .contents(TaskSaveReqDto.contents)
                    .deadline(parseDatetime(TaskSaveReqDto.deadline))
                    .isCompleted(TaskSaveReqDto.isCompleted)
                    .build();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class TaskEditRequestDto {
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