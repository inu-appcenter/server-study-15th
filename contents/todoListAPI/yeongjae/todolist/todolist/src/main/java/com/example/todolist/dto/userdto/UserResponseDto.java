package com.example.todolist.dto.userdto;

import com.example.todolist.domain.User;
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
}
