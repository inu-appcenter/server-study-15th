package com.example.todolist.controller;


import com.example.todolist.dto.TodoCheckReqDto;
import com.example.todolist.dto.TodoPageRespDto;
import com.example.todolist.dto.TodoReqDto;
import com.example.todolist.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping()
    public ResponseEntity<String> create(Long memberId, @RequestBody TodoReqDto todoReqDto) {
        Long save = todoService.save(memberId, todoReqDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(save + "번 할 일 등록 완료");
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> updateChecked(@PathVariable Long id, @RequestBody TodoCheckReqDto todoCheckReqDto) {
        Long updateChecked = todoService.updateChecked(id, todoCheckReqDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(updateChecked + "번 Checked 수정 완료");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTodo(@PathVariable Long id, @RequestBody TodoReqDto todoReqDto) {
        Long update = todoService.update(id, todoReqDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(update + "번 할 일 수정 완료");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(id + "번 할 일 삭제 완료");
    }

    @GetMapping()
    public ResponseEntity<List<TodoPageRespDto>> readAllTodo(@RequestBody Long memberId) {
        List<TodoPageRespDto> todos = todoService.findAll(memberId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(todos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoPageRespDto> readOneTodo(@PathVariable Long id) {
        TodoPageRespDto todo = todoService.findOne(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(todo);
    }

}
