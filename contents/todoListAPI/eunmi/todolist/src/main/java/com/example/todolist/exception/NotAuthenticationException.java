package com.example.todolist.exception;

public class NotAuthenticationException extends RuntimeException {
    public NotAuthenticationException() {
        super();
    }

    public NotAuthenticationException(String message) {
        super(message);
    }

    public NotAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAuthenticationException(Throwable cause) {
        super(cause);
    }
}
