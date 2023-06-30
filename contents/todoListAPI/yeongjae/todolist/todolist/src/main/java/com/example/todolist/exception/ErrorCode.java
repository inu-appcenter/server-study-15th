package com.example.todolist.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.client.HttpClientErrorException.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // == 200 == //
    SUCCESS(OK,"OK"),

    // == 4xx == //
    USER_NOT_FOUND_EXCEPTION(NOT_FOUND, "해당 ID 를 가진 유저가 없습니다."),
    TODO_NOT_FOUND_EXCEPTION(NOT_FOUND, "해당 id 를 가진 task 가 없습니다."),
    USER_NOT_HAVE_TODO(NOT_FOUND, "해당 task 를 가진 user 가 아닙니다."),
    SAME_USER_EXCEPTION(CONFLICT, "동일한 이름을 가진 회원이 존재합니다.");

    private final HttpStatus status;
    private final String msg;
}
