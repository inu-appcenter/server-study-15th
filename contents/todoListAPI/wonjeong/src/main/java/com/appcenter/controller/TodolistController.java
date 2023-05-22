package com.appcenter.controller;

import com.appcenter.data.dto.request.TodolistRequestDTO;
import com.appcenter.data.dto.response.TodolistResponseDTO;
import com.appcenter.service.TodolistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todolist")
public class TodolistController {

    // TodolistService 상수 선언
    private final TodolistService todolistService;

    public TodolistController(TodolistService todolistService) {
        this.todolistService = todolistService;
    }

    @GetMapping()
    public ResponseEntity<TodolistResponseDTO> getContent(Long id) throws Exception{
        TodolistResponseDTO todolistResponseDTO = todolistService.getContent(id);

        return ResponseEntity.status(HttpStatus.OK).body(todolistResponseDTO);
    }

    @PostMapping()
    public ResponseEntity<TodolistResponseDTO> createContent(@RequestBody TodolistRequestDTO todolistRequestDTO, Long id) throws Exception {
        TodolistResponseDTO todolistResponseDTO = todolistService.savedContent(id, todolistRequestDTO);

        return  ResponseEntity.status(HttpStatus.CREATED).body(todolistResponseDTO);
    }

    @PutMapping()
    public ResponseEntity<TodolistResponseDTO> updateContent(@RequestBody TodolistRequestDTO todolistRequestDTO, Long id) throws Exception {
        TodolistResponseDTO todolistResponseDTO = todolistService.updateContent(id, todolistRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(todolistResponseDTO);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteContent(Long id) throws Exception {
        todolistService.deleteContent(id);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 글이 삭제되었습니다.");
    }

}
