package com.example.todolist.controller;

import com.example.todolist.domain.Todo;
import com.example.todolist.dto.ResponseDto;
import com.example.todolist.dto.taskdto.TodoRequestDto.TodoSaveReqDto;
import com.example.todolist.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.todolist.dto.taskdto.TodoRequestDto.*;
import static com.example.todolist.dto.taskdto.TodoResponseDto.*;
import static com.example.todolist.dto.userdto.UserRequestDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    @GetMapping("")
    @Operation(summary = "Todo 조회", description = "Authorize 에 토큰값을 삽입하고 실행해주세요")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Todo 조회 성공")})
    public ResponseEntity<?> findTodo(UserId userId) {
        List<Todo> todoList = todoService.findTodoById(userId.getId());
        TodoListRespDto todoListRespDto = new TodoListRespDto(todoList);
        return new ResponseEntity<>(new ResponseDto<>(1, "Todo 조회 성공", todoListRespDto),HttpStatus.OK);
    }

    @PostMapping("")
    @Operation(summary = "Todo 작성", description = "바디에 {title, contents, deadLine, isCompleted} 를 json 형식으로 보내주시고" +
            " Authorize 에 토큰값을 삽입시켜 주시면됩니다")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Todo 작성 성공")})
    public ResponseEntity<?> writeTodo(@RequestBody @Valid final TodoSaveReqDto TodoSaveReqDto, UserId userId) {
        TodoSaveRespDto todoSaveRespDto = todoService.writeTodo(TodoSaveReqDto, userId.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "Todo 작성 성공", todoSaveRespDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Todo 삭제", description = "파라미터에 todoId를 보내주세요")
    @ApiResponse(responseCode = "200", description = "Todo 삭제 성공")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id, UserId userId) {
        TodoDeleteRespDto todoDeleteRespDto = todoService.deleteTodo(id, userId.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "Todo 삭제 성공", todoDeleteRespDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Todo 수정", description = "파라미터에 todoId, " +
            "바디에 {title, contents, deadLine, isCompleted} 을 json 형식으로 보내주시면 됩니다.")
    @ApiResponse(responseCode = "200", description = "Todo 수정 성공")
    public ResponseEntity<?> editTodo(@PathVariable Long id, @RequestBody @Valid final TodoEditRequestDto todoEditRequestDto, UserId userId) {
        TodoEditRespDto todoEditRespDto = todoService.editTodo(id, todoEditRequestDto, userId.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "Todo 수정 성공", todoEditRespDto), HttpStatus.CREATED);
    }
}