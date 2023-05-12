package com.example.todo.common.exception.todo;

import com.example.todo.common.exception.StatusEnum;
import lombok.Getter;

@Getter
public class TodoNotFoundException extends IllegalArgumentException {

    private final StatusEnum status;

    private static final String message = "해당 id를 가진 todo가 없습니다. 아이디 값을 다시 한번 확인하세요.";

    public TodoNotFoundException() {
        super(message);
        this.status = StatusEnum.TODO_NOT_FOUND;
    }

}
