package com.example.todo.common.exception;

public enum StatusEnum {
    BAD_REQUEST(400, "BAD_REQUEST"),
    USER_NOT_FOUND(404,"USER_NOT_FOUND"),
    ALREADY_USER_EXIST(403, "ALREADY_USER_EXIST"),
    TODO_NOT_FOUND(404,"USER_NOT_FOUND");

    private final int statusCode;
    private final String code;

    private StatusEnum(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }
}

