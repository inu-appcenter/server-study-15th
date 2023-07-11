package com.example.todolist.dto.taskdto;

import com.example.todolist.domain.Todo;
import com.example.todolist.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "잘못된 기한일 형식입니다.")
        private String deadlineDay;

        private String deadlineTime;

        private boolean isCompleted;

        public Todo changeEntity(TodoSaveReqDto TodoSaveReqDto, User user) {
            LocalDate date = LocalDate.parse(deadlineDay);
            LocalTime time = LocalTime.parse(deadlineTime);

            return Todo.builder()
                    .title(TodoSaveReqDto.title)
                    .contents(TodoSaveReqDto.contents)
                    .user(user)
                    .deadline(LocalDateTime.of(date, time))
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
        private String title;
        private String contents;

        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "잘못된 기한일 형식입니다.")
        private String deadline;
        private boolean isCompleted;
    }

    @Getter
    @Setter
    public static class TodoFindRequestDto {
        @NotEmpty
        private String title;
    }

    public static LocalDateTime parseDatetime(String deadline) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(deadline, dateTimeFormatter).atStartOfDay();
    }
}