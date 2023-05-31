package com.example.todolist.controller;


import com.example.todolist.domain.Message;
import com.example.todolist.dto.TodoCheckReqDto;
import com.example.todolist.dto.TodoPageRespDto;
import com.example.todolist.dto.TodoReqDto;
import com.example.todolist.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    private static final String SUCCESS_TODO_CREATE_MESSAGE = "todo가 저장 되었습니다.";
    private static final String SUCCESS_TODO_UPDATE_MESSAGE = "todo가 수정되었습니다.";
    private static final String SUCCESS_TODO_CHECKED_UPDATE_MESSAGE = "수행여부가 수정되었습니다.";
    private static final String SUCCESS_TODO_DELETE_MESSAGE = "todo가 삭제되었습니다.";

    @PostMapping()
    public ResponseEntity<Message> create(Long memberId, @RequestBody TodoReqDto todoReqDto) {
        Long save = todoService.save(memberId, todoReqDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Message(SUCCESS_TODO_CREATE_MESSAGE));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Message> updateChecked(@PathVariable Long id, @RequestBody TodoCheckReqDto todoCheckReqDto) {
        Long updateChecked = todoService.updateChecked(id, todoCheckReqDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Message(SUCCESS_TODO_CHECKED_UPDATE_MESSAGE));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateTodo(@PathVariable Long id, @RequestBody TodoReqDto todoReqDto) {
        Long update = todoService.update(id, todoReqDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Message(SUCCESS_TODO_UPDATE_MESSAGE));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Message(SUCCESS_TODO_DELETE_MESSAGE));
    }

    @GetMapping()
    public ResponseEntity<List<TodoPageRespDto>> readAllTodo(@RequestBody Long memberId) {
        List<TodoPageRespDto> todos = todoService.findAll(memberId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(todos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Page<TodoPageRespDto>> readTodo(@PathVariable Long id, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Page<TodoPageRespDto> todos = todoService.findTodoList(id, page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(todos);
    }

}
