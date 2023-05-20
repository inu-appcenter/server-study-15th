package com.example.todolist.dto;

import com.example.todolist.domain.Task;
import com.example.todolist.domain.User;
import lombok.Getter;

@Getter
public class UserDto {

    private String name;
    private String password;
    private String email;

    public User changeEntity(UserDto userDto) {
        return User.builder()
                .name(userDto.name)
                .password(userDto.password)
                .email(userDto.email)
                .build();
    }
}
