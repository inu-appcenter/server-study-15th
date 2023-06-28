package com.example.todolist.common.controller;

import com.example.todolist.domain.Message;
import com.example.todolist.exception.AccessException;
import com.example.todolist.exception.NotAuthenticationException;
import com.example.todolist.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({NotAuthenticationException.class})
    public Message NotAuthenticationException(NotAuthenticationException e) {
        Message message = new Message(e.getMessage());
        log.info(message.getMessage());
        return message;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({AccessException.class})
    public Message AccessException(NotAuthenticationException e) {
        Message message = new Message(e.getMessage());
        log.info(message.getMessage());
        return message;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NotFoundException.class})
    public Message NotFoundException(NotFoundException e) {
        Message message = new Message(e.getMessage());
        log.info(message.getMessage());
        return message;
    }
}
