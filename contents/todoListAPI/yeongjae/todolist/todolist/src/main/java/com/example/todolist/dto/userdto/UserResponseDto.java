package com.example.todolist.dto.userdto;

import com.example.todolist.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserResponseDto {

    @Getter
    @Setter
    public static class UserJoinRespDto {
        @NotEmpty
        @Size(min = 5, max = 15)
        private String name;
        @NotEmpty
        @Size(min = 8, max = 20)
        private String password;
        @Email
        @NotEmpty
        private String email;

        public UserJoinRespDto(User user) {
            this.name = user.getName();
            this.password = user.getPassword();
            this.email = user.getEmail();
        }
    }

    @Getter
    @Setter
    public static class UserFindRespDto {
        @NotEmpty
        @Size(min = 5, max = 15)
        private String name;
        @Email
        @NotEmpty
        private String email;

        public UserFindRespDto(User user) {
            this.name = user.getName();
            this.email = user.getEmail();
        }
    }

    @Getter
    @Setter
    public static class UserEditRespDto {
        @NotEmpty
        @Size(min = 5, max = 15)
        @Schema(example = "lee2")
        private String name;
        @NotEmpty
        @Size(min = 8, max = 20)
        @Schema(example = "dddd1235")
        private String password;
        @Schema(example = "dudqk9696@naver.com")
        @Email
        @NotEmpty
        private String email;

        public UserEditRespDto(User user) {
            this.name = user.getName();
            this.password = user.getPassword();
            this.email = user.getEmail();
        }
    }

    @Getter
    @Setter
    public static class UserDeleteRespDto {
        @NotEmpty
        @Size(min = 5, max = 15)
        private String name;
        @NotEmpty
        @Size(min = 8, max = 20)
        private String password;
        @Email
        @NotEmpty
        private String email;

        public UserDeleteRespDto(User user) {
            this.name = user.getName();
            this.password = user.getPassword();
            this.email = user.getEmail();
        }
    }

    @Getter
    @Setter
    public static class UserLoginRespDto {
        @NotEmpty
        @Size(min = 5, max = 15)
        private String name;
        private String token;

        public UserLoginRespDto(String name, String token) {
            this.name = name;
            this.token = token;
        }
    }
}