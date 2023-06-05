package com.example.todolist.exception.user;

import lombok.Getter;

@Getter
public class UserNotFoundException extends IllegalArgumentException {

    private static final String message = "해당 ID 를 가진 유저가 없습니다.";

    public UserNotFoundException() {
        super(message);
    }

}
