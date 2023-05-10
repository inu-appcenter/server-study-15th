package com.example.todolist.dto;

import com.example.todolist.domain.Task;
import lombok.Getter;
import net.bytebuddy.asm.Advice;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class TaskDto {

    @NotEmpty
    private String title;
    private String contents;
    @NotEmpty
    private String deadline;

    private boolean isCompleted;

    public Task changeEntity(TaskDto taskDto) {
        return Task.builder()
                .title(taskDto.title)
                .contents(taskDto.contents)
                .deadline(parseDatetime(taskDto.deadline))
                .isCompleted(taskDto.isCompleted)
                .build();
    }

    public LocalDateTime parseDatetime(String deadline) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime localDateTime = LocalDate.parse(deadline, dateTimeFormatter).atStartOfDay();
        return localDateTime;
    }
}