package com.example.todo.common.exception.token;

import com.example.todo.common.exception.StatusEnum;
import lombok.Getter;

@Getter
public class TokenNotFoundException extends IllegalArgumentException {
    private final StatusEnum status;
    private static final String message = "해당 토큰을 찾을 수 없습니다.";

    public TokenNotFoundException() {
        super(message);
        this.status = StatusEnum.TOKEN_NOT_EXIST;
    }
}
