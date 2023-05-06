package com.example.todolist.controller;


import com.example.todolist.domain.Todo;
import com.example.todolist.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Tag(name = "Todo", description = "Todo 관련 api")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/findTodo")
    @Operation(summary = "Todo 리스트", description = "Todo 리스트 반환 api 입니다")
    public List<Todo> findTodo() {
        return todoService.findAll();
    }

    @PostMapping("/writeTodo")
    @Operation(summary = "Todo 작성", description = "Todo 작성 api 입니다")
    public void writeTodo(Todo todo) {
        todoService.writeTodo(todo);
    }

}
