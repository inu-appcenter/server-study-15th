package com.example.todolist.controller;

import com.example.todolist.dto.ResponseDto;
import com.example.todolist.dto.taskdto.TodoRequestDto.TodoSaveReqDto;
import com.example.todolist.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.todolist.dto.taskdto.TodoRequestDto.*;
import static com.example.todolist.dto.taskdto.TodoResponseDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;

    @GetMapping("")
    @Operation(summary = "Todo 조회", description = "Todo 리스트를 조회합니다")
    @ApiResponse(responseCode = "200", description = "Todo 조회 성공")
    public ResponseEntity<?> findTodo() {
        return new ResponseEntity<>(new ResponseDto<>(1,"Todo 조회 성공", todoService.findAll()), HttpStatus.OK);
    }

    @PostMapping("")
    @Operation(summary = "Todo 작성", description = "Todo 작성 api 입니다")
    @ApiResponse(responseCode = "201", description = "Todo 작성 성공")
    public ResponseEntity<?> writeTodo(@RequestBody TodoSaveReqDto TodoSaveReqDto) {
        TaskSaveRespDto taskSaveRespDto = todoService.writeTodo(TodoSaveReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "Todo 작성 성공", taskSaveRespDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Todo 삭제", description = "Todo 삭제 api 입니다")
    @ApiResponse(responseCode = "200", description = "Todo 삭제 성공")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id) {
        TaskDeleteRespDto taskDeleteRespDto = todoService.deleteTodo(id);
        return new ResponseEntity<>(new ResponseDto<>(1, "Todo 삭제 성공", taskDeleteRespDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Todo 수정", description = "Todo 수정 api 입니다")
    @ApiResponse(responseCode = "200", description = "Todo 수정 성공")
    public ResponseEntity<?> editTodo(@PathVariable Long id, @RequestBody TodoEditRequestDto todoEditRequestDto) {
        TaskEditRespDto taskEditRespDto = todoService.editTodo(id, todoEditRequestDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "Todo 수정 성공", taskEditRespDto), HttpStatus.CREATED);
    }
}