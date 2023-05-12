package com.example.todolist.controller;


import com.example.todolist.domain.Todo;
import com.example.todolist.dto.TodoReqDto;
import com.example.todolist.dto.TodoRespDto;
import com.example.todolist.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping()
    public String create(Long memberId, @RequestBody TodoReqDto todoReqDto) {
        Long save = todoService.save(memberId, todoReqDto);
        return save + "번 할 일 등록 완료";
    }

    @PutMapping("/{id}")
    public String updateTodo(@PathVariable Long id, @RequestBody TodoReqDto todoReqDto) {
        Long update = todoService.update(id, todoReqDto);
        return update + "번 할 일 수정 완료";
    }

    @DeleteMapping("/{id}")
    public String deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return id + "번 할 일 삭제 완료";
    }

    @GetMapping()
    public List<Todo> readAllTodo(@RequestBody Long memberId) {
        return todoService.findAll(memberId);
    }

    @GetMapping("/{id}")
    public TodoRespDto readOneTodo(@PathVariable Long id) {
        return todoService.findOne(id);
    }
}
