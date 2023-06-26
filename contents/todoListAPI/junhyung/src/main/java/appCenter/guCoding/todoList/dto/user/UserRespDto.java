package appCenter.guCoding.todoList.dto.user;

import appCenter.guCoding.todoList.domain.task.Task;
import appCenter.guCoding.todoList.domain.user.User;
import appCenter.guCoding.todoList.util.CustomDateUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserRespDto {

    @Getter
    @Setter
    public static class JoinRespDto {
        private Long id;
        private String username;

        public JoinRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
        }
    }

    @Getter
    @Setter
    public static class UserEditRespDto {

        private String username;

        public UserEditRespDto(User user) {
            this.username = user.getUsername();
        }
    }

    @Getter
    @Setter
    public static class LoginRespDto {
        private Long id;
        private String username;
        private String email;
        private String createdAt;

        private String jwtToken;

        public LoginRespDto(User user, String jwtToken) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.createdAt = CustomDateUtil.toStringFormat(user.getCreateDate());
            this.jwtToken = jwtToken;
        }
    }

    @Getter
    @Setter
    public static class UserListRespDto {
        private List<UserDto> users = new ArrayList<>();

        public UserListRespDto(List<User> users) {
            this.users = users.stream().map(UserDto::new).collect(Collectors.toList());
        }

        @Getter
        @Setter
        public class UserDto {
            private Long id;
            private String username;
            private String email;
            private List<UserTaskRespDto> tasks = new ArrayList<>();

            public UserDto(User user) {
                this.id = user.getId();
                this.username = user.getUsername();
                this.email = user.getEmail();
                this.tasks = user.getTaskList().stream().map(UserTaskRespDto::new).collect(Collectors.toList());
            }


        }

        @Getter
        @Setter
        public class UserTaskRespDto {
            private Long id;
            private String title;
            private String description;
            private Boolean isCompleted;
            private LocalDateTime deadline;

            public UserTaskRespDto(Task task) {
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
    public static class UserMyInfoRespDto {
        private Long id;
        private String username;
        private String email;
        private List<TaskDto> tasks = new ArrayList<>();

        public UserMyInfoRespDto(User user, List<Task> tasks) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.tasks = tasks.stream().map(TaskDto::new).collect(Collectors.toList());
        }

        @Getter
        @Setter
        public class TaskDto {
            private Long id;
            private String title;
            private String description;
            private LocalDateTime deadline;

            public TaskDto(Task task) {
                this.id = task.getId();
                this.title = task.getTitle();
                this.description = task.getDescription();
                this.deadline = task.getDeadline();
            }
        }
    }
}
