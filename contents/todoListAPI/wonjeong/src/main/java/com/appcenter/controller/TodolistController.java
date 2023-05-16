package com.appcenter.controller;

import com.appcenter.data.dto.TodolistDTO;
import com.appcenter.data.dto.response.TodolistResponseDTO;
import com.appcenter.service.TodolistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todolist")
public class TodolistController {

    // TodolistService 상수 선언
    private final TodolistService todolistService;

    @Autowired
    public TodolistController(TodolistService todolistService) {
        this.todolistService = todolistService;
    }

    @GetMapping()
    public ResponseEntity<TodolistResponseDTO> getContent(Long number) {
        TodolistResponseDTO todolistResponseDTO = todolistService.getContent(number);

        return ResponseEntity.status(HttpStatus.OK).body(todolistResponseDTO);
    }

    @PostMapping()
    public ResponseEntity<TodolistResponseDTO> createContent(@RequestBody TodolistDTO todolistDTO) {
        TodolistResponseDTO todolistResponseDTO = todolistService.savedContent(todolistDTO);

        return  ResponseEntity.status(HttpStatus.OK).body(todolistResponseDTO);
    }
}
