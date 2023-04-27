package com.example.todo.dto.data;

import com.example.todo.domain.Todo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoListData {

    private final Long todoId;

    private final String contents;

    private final LocalDateTime createdDate;

    private final LocalDateTime modifiedDate;

//    private final UserData writer;

    public TodoListData(final Todo todo) {
        this.todoId = todo.getId();
        this.contents = todo.getContents();
        this.createdDate = todo.getCreatedDate();
        this.modifiedDate = todo.getModifiedDate();
//        UserData userData = new UserData(todo.getUser());
//        this.writer = userData;
    }
}
