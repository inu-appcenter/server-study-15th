package com.example.todolist.controller;

import com.example.todolist.dto.ResponseDto;
import com.example.todolist.dto.taskdto.TaskRequestDto;
import com.example.todolist.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.todolist.dto.taskdto.TaskResponseDto.*;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/Todo")
    @Operation(summary = "Todo 조회", description = "Todo 리스트를 조회합니다")
    @ApiResponse(responseCode = "200", description = "Todo 조회 성공")
    public ResponseEntity<?> findTodo() {
        return new ResponseEntity<>(new ResponseDto<>(1,"Todo 조회 성공",todoService.findAll()), HttpStatus.OK);
    }

    @PostMapping("/Todo")
    @Operation(summary = "Todo 작성", description = "Todo 작성 api 입니다")
    @ApiResponse(responseCode = "201", description = "Todo 작성 성공")
    public ResponseEntity<?> writeTodo(@RequestBody TaskRequestDto.TaskSaveReqDto TaskSaveReqDto) {
        TaskSaveRespDto taskSaveRespDto = todoService.writeTodo(TaskSaveReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "Todo 작성 성공", taskSaveRespDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/Todo/{id}")
    @Operation(summary = "Todo 삭제", description = "Todo 삭제 api 입니다")
    @ApiResponse(responseCode = "200", description = "Todo 삭제 성공")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id) {
        TaskDeleteRespDto taskDeleteRespDto = todoService.deleteTodo(id);
        return new ResponseEntity<>(new ResponseDto<>(1, "Todo 삭제 성공", taskDeleteRespDto), HttpStatus.OK);
    }

    @PutMapping("/Todo/{id}")
    @Operation(summary = "Todo 수정", description = "Todo 수정 api 입니다")
    @ApiResponse(responseCode = "200", description = "Todo 수정 성공")
    public ResponseEntity<?> editTodo(@PathVariable Long id, @RequestBody TaskRequestDto.TaskEditRequestDto taskEditRequestDto) throws Exception {
        TaskEditRespDto taskEditRespDto = todoService.editTodo(id, taskEditRequestDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "Todo 수정 성공", taskEditRespDto), HttpStatus.CREATED);
    }
}