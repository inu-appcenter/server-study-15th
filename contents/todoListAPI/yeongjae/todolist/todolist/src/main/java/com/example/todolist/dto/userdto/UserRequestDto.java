package com.example.todolist.dto.userdto;

import com.example.todolist.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UserRequestDto {

    @Getter
    @Setter
    public static class UserJoinReqDto {
        private String name;
        private String password;
        private String email;

        public User changeEntity(UserJoinReqDto UserJoinReqDto) {
            return User.builder()
                    .name(UserJoinReqDto.name)
                    .password(UserJoinReqDto.password)
                    .email(UserJoinReqDto.email)
                    .build();
        }
    }
}
