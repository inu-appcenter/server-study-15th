package com.appcenter.controller;

import com.appcenter.data.dto.request.TodolistRequestDTO;
import com.appcenter.data.dto.response.TodolistResponseDTO;
import com.appcenter.service.TodolistService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todolist")
public class TodolistController {

    // TodolistService 상수 선언
    private final TodolistService todolistService;

    public TodolistController(TodolistService todolistService) {
        this.todolistService = todolistService;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "발급 받은 Token",
                    required = true, dataType = "String", paramType = "header")
    })
    @GetMapping()
    public ResponseEntity<TodolistResponseDTO> getContent(Long id) throws Exception{
        TodolistResponseDTO todolistResponseDTO = todolistService.getContent(id);

        return ResponseEntity.status(HttpStatus.OK).body(todolistResponseDTO);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "발급 받은 Token",
                    required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/getContentList")
    public List<TodolistResponseDTO> getContentList(Long id) throws Exception{

        return todolistService.getContentsList(id);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "발급 받은 Token",
                    required = true, dataType = "String", paramType = "header")
    })
    @PostMapping()
    public ResponseEntity<TodolistResponseDTO> createContent(@RequestBody TodolistRequestDTO todolistRequestDTO, Long id) throws Exception {
        TodolistResponseDTO todolistResponseDTO = todolistService.savedContent(id, todolistRequestDTO);

        return  ResponseEntity.status(HttpStatus.CREATED).body(todolistResponseDTO);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "발급 받은 Token",
                    required = true, dataType = "String", paramType = "header")
    })
    @PutMapping()
    public ResponseEntity<TodolistResponseDTO> updateContent(@RequestBody TodolistRequestDTO todolistRequestDTO, Long id) throws Exception {
        TodolistResponseDTO todolistResponseDTO = todolistService.updateContent(id, todolistRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(todolistResponseDTO);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "발급 받은 Token",
                    required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping()
    public ResponseEntity<String> deleteContent(Long id) throws Exception {
        todolistService.deleteContent(id);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 글이 삭제되었습니다.");
    }

}
