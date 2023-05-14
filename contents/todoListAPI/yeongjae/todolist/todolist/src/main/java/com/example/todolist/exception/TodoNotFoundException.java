package com.example.todolist.exception;

import lombok.Getter;

@Getter
public class TodoNotFoundException extends IllegalArgumentException{

    private static final String message = "해당 id를 가진 task 가 없습니다.";

    public TodoNotFoundException() {
        super(message);
    }
}
