package com.example.todolist.controller;

import com.example.todolist.dto.TaskDto;
import com.example.todolist.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Tag(name = "Todo", description = "Todo 관련 api")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/Todo")
    @Operation(summary = "Todo 리스트", description = "Todo 리스트 반환 api 입니다")
    public ResponseEntity<?> findTodo() {
        return new ResponseEntity<>(todoService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/Todo")
    @Operation(summary = "Todo 작성", description = "Todo 작성 api 입니다")
    public ResponseEntity<?> writeTodo(@RequestBody TaskDto taskDto) {
        todoService.writeTodo(taskDto);
        return new ResponseEntity<>(taskDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/Todo/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return new ResponseEntity<>(id,HttpStatus.OK);
    }

    @PutMapping("/Todo/{id}")
    public ResponseEntity<?> editTodo(@PathVariable Long id, @RequestBody TaskDto taskDto) throws Exception {
        todoService.editTodo(id, taskDto);
        return new ResponseEntity<>(taskDto, HttpStatus.OK);
    }
}