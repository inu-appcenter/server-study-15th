package com.example.todo.dto.response;

import com.example.todo.dto.data.TodoListData;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Getter
public class GetTodoListResponse {
    private Slice<TodoListData> todoSlice;

    private String message;

    @Builder
    public GetTodoListResponse(Slice<TodoListData> todoSlice, String message) {
        this.todoSlice = todoSlice;
        this.message = message;
    }
}
