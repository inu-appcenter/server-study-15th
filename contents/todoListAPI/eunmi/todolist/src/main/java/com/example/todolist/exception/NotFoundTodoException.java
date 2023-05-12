package com.example.todolist.exception;

public class NotFoundTodoException extends RuntimeException {
    public NotFoundTodoException() {
        super();
    }

    public NotFoundTodoException(String message) {
        super(message);
    }

    public NotFoundTodoException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundTodoException(Throwable cause) {
        super(cause);
    }
}
