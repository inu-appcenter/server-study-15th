package com.example.todolist.dto.userdto;

import com.example.todolist.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class UserResponseDto {

    @Getter
    @Setter
    public static class UserJoinRespDto {
        private String name;
        private String password;
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
        private String name;
        private String email;

        public UserFindRespDto(User user) {
            this.name = user.getName();
            this.email = user.getEmail();
        }
    }

    @Getter
    @Setter
    public static class UserEditRespDto {
        @Schema(example = "lee2")
        private String name;
        @Schema(example = "dddd1235")
        private String password;
        @Schema(example = "dudqk9696@naver.com")
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
        private String name;
        private String password;
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
        private String name;
        private String token;

        public UserLoginRespDto(String name, String token) {
            this.name = name;
            this.token = token;
        }
    }
}