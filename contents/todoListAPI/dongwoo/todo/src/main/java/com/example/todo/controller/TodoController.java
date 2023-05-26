package com.example.todo.controller;

import com.example.todo.common.dto.CommonResponse;
import com.example.todo.dto.data.TodoListData;
import com.example.todo.dto.data.UserId;
import com.example.todo.dto.request.CreateTodoRequest;
import com.example.todo.dto.request.UpdateTodoRequest;
import com.example.todo.dto.response.GetTodoListResponse;
import com.example.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/todos")
@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "user", description = "user api 입니다")
public class TodoController {

    private final TodoService todoService;

    @Operation(summary = "todo 생성", description = "바디에 {contents, deadLine} 을 json 형식으로 보내주시면 됩니다.")
    @PostMapping("")
    public ResponseEntity<CommonResponse> createTodo(@Valid @RequestBody CreateTodoRequest request, UserId userId) {

        todoService.createTodo(request, userId.getId());

        CommonResponse response = new CommonResponse("투두 저장 성공 했습니다");

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @Operation(summary = "todo 삭제", description = "파라미터에 todoId 보내주시면 됩니다.")
    @DeleteMapping("/{todoId}")
    public ResponseEntity<CommonResponse> deleteTodo(@PathVariable("todoId") Long todoId) {

        todoService.deleteTodo(todoId);
        CommonResponse response = new CommonResponse("투두 삭제 성공 했습니다");

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @Operation(summary = "todo 수정", description = "파라미터에 todoId, 바디에 {contents} 을 json 형식으로 보내주시면 됩니다.")
    @PatchMapping("{todoId}")
    public ResponseEntity<CommonResponse> updateTodo(@PathVariable Long todoId, @Valid @RequestBody UpdateTodoRequest request) {

        todoService.updateTodo(todoId, request);

        CommonResponse response = new CommonResponse("투두 수정 성공 했습니다");

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @Operation(summary = "최신순 todo 리스트 조회(무한 스크롤)", description = "파라미터에 page, size 보내주시면 됩니다.")
    @GetMapping("")
    public ResponseEntity<GetTodoListResponse> getVoteList(@RequestParam int page, @RequestParam int size) {
        Slice<TodoListData> todoListData = todoService.getTodoList(page, size);
        GetTodoListResponse todoListResponse = GetTodoListResponse.builder()
                .todoSlice(todoListData)
                .message("최신순 todo 리스트 조회를 성공 했습니다")
                .build();
        return new ResponseEntity(todoListResponse, HttpStatus.OK);
    }

    @Operation(summary = "todo 수행 완료", description = "파라미터에 수행 완료된 todoId 보내주시면 됩니다. ")
    @GetMapping("/{todoId}")
    public ResponseEntity<CommonResponse> doTodo(@PathVariable("todoId") Long todoId) {

        todoService.doTodo(todoId);

        CommonResponse response = new CommonResponse("투두 수행 완료 성공 했습니다");

        return new ResponseEntity(response, HttpStatus.OK);
    }

}
