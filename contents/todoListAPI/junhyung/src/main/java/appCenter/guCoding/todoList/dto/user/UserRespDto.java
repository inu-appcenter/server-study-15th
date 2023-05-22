package appCenter.guCoding.todoList.dto.user;

import appCenter.guCoding.todoList.domain.user.User;

import appCenter.guCoding.todoList.util.CustomDateUtil;
import lombok.Getter;
import lombok.Setter;

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

            public UserDto(User user) {
                this.id = user.getId();
                this.username = user.getUsername();
                this.email = user.getEmail();
            }
        }
    }
}
