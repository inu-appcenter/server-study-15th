package appCenter.guCoding.todoList.dto.task;

import appCenter.guCoding.todoList.domain.task.Task;
import appCenter.guCoding.todoList.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskRespDto {

    @Getter
    @Setter
    public static class TaskSaveRespDto {
        private Long id;
        private String title;
        private LocalDateTime deadline;


        public TaskSaveRespDto(Task task) {
            this.id = task.getId();
            this.title = task.getTitle();
            this.deadline = task.getDeadline();
        }

    }

    @Getter
    @Setter
    public static class TaskListRespDto {
        // 리포지토리로 모든 Task 를 찾음 ->Task 하나하나 DTO 변환 -> DTO 모아둔 List 반환

        private List<TaskOneDto> tasks = new ArrayList<>();

        public TaskListRespDto(List<Task> tasks) {
            this.tasks = tasks.stream()
                    .map((task) -> new TaskOneDto(task))
                    .collect(Collectors.toList());
        }

        @Getter
        @Setter
        public class TaskOneDto {
            private Long id;
            private String title;
            private String description;
            private Boolean isCompleted;
            private LocalDateTime deadline;

            public TaskOneDto(Task task) {
                this.id = task.getId();
                this.title = task.getTitle();
                this.description = task.getDescription();
                this.isCompleted = task.getIsCompleted();
                this.deadline = task.getDeadline();
            }
        }
    }

    @Getter
    @Setter
    public static class TaskEditRespDto {
        private Long id;
        private String title;
        private String description;
        private Boolean isCompleted;
        private LocalDateTime deadline;

        public TaskEditRespDto(Task task) {
            this.id = task.getId();
            this.title = task.getTitle();
            this.description = task.getDescription();
            this.isCompleted = task.getIsCompleted();
            this.deadline = task.getDeadline();
        }
    }

    @Getter
    @Setter
    public static class TaskIdRespDto {
        private Long id;
        private String title;
        private String description;
        private Boolean isCompleted;
        private LocalDateTime deadline;

        public TaskIdRespDto(Task task) {
            this.id = task.getId();
            this.title = task.getTitle();
            this.description = task.getDescription();
            this.isCompleted = task.getIsCompleted();
            this.deadline = task.getDeadline();
        }
    }

    @Getter
    @Setter
    public static class TaskTitleRespDto {
        private Long id;
        private String title;
        private String description;
        private Boolean isCompleted;
        private LocalDateTime deadline;

        public TaskTitleRespDto(Task task) {
            this.id = task.getId();
            this.title = task.getTitle();
            this.description = task.getDescription();
            this.isCompleted = task.getIsCompleted();
            this.deadline = task.getDeadline();
        }
    }
}
