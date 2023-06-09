package appCenter.guCoding.todoList.dto.task;

import appCenter.guCoding.todoList.domain.task.Task;
import appCenter.guCoding.todoList.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static appCenter.guCoding.todoList.util.CustomDateUtil.toDateTimeFormat;

public class TaskReqDto {

    @Getter
    @Setter
    public static class TaskSaveReqDto {

        @Schema(example = "테스트 제목")
        @NotEmpty
        private String title;

        @Schema(example = "테스트 본문")
        private String description;

        @Schema(example = "2023-05-01")
        @NotEmpty
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "잘못된 기한일 형식입니다.")
        private String deadline;

        public Task toEntity(User user) {
            return Task.builder()
                    .title(title)
                    .description(description)
                    .deadline(toDateTimeFormat(deadline)) // question
                    .isCompleted(false)
                    .user(user)
                    .build();

        }

    }

    @Getter
    @Setter
    public static class TaskEditReqDto {

        @Schema(example = "수정된 테스트 제목")
        private String title;
        @Schema(example = "수정된 테스트 본문")
        private String description;
        @Schema(example = "2023-05-02")
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "잘못된 기한일 형식입니다.")
        private String deadline;
        @Schema(example = "true")
        private Boolean isCompleted;

        public Task toEntity() {
            return Task.builder()
                    .title(title)
                    .description(description)
                    .deadline(parseDateTime(deadline)) // question
                    .isCompleted(isCompleted)
//                    .user()
                    .build();

        }

        private LocalDateTime parseDateTime(String deadline) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime parseDeadline = LocalDate.parse(deadline, formatter).atStartOfDay();
            return parseDeadline;

        }


    }
}
