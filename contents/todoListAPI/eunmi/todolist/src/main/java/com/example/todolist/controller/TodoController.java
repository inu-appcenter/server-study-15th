package com.example.todolist.controller;


import com.example.todolist.domain.Member;
import com.example.todolist.domain.Message;
import com.example.todolist.dto.TodoCheckReqDto;
import com.example.todolist.dto.TodoPageRespDto;
import com.example.todolist.dto.TodoReqDto;
import com.example.todolist.service.TodoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/todo")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    private static final String SUCCESS_TODO_CREATE_MESSAGE = "todo가 저장 되었습니다.";
    private static final String SUCCESS_TODO_UPDATE_MESSAGE = "todo가 수정되었습니다.";
    private static final String SUCCESS_TODO_CHECKED_UPDATE_MESSAGE = "수행여부가 수정되었습니다.";
    private static final String SUCCESS_TODO_DELETE_MESSAGE = "todo가 삭제되었습니다.";


    @ApiOperation(value = "할 일 등록", notes = "멤버id, 등록할 할일 정보 입력")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "accessToken", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping()
    public ResponseEntity<Message> create(@RequestBody TodoReqDto todoReqDto) {
        todoService.save(getMember().getId(), todoReqDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Message(SUCCESS_TODO_CREATE_MESSAGE));
    }

    @ApiOperation(value = "완료 여부 체크 수정", notes = "해당 todo id값과 체크 여부 json으로 입력")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "accessToken", required = true, dataType = "String", paramType = "header")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Message> updateChecked(@PathVariable Long id, @RequestBody TodoCheckReqDto todoCheckReqDto) {
        todoService.updateChecked(getMember().getId(), id, todoCheckReqDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Message(SUCCESS_TODO_CHECKED_UPDATE_MESSAGE));
    }

    @ApiOperation(value = "할 일 수정", notes = "수정할 todo id, 수정할 할 일 정보 입력")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "accessToken", required = true, dataType = "String", paramType = "header")
    })
    @PutMapping ("/{id}")
    public ResponseEntity<Message> updateTodo(@PathVariable Long id, @RequestBody TodoReqDto todoReqDto) {
        todoService.update(getMember().getId(), id, todoReqDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Message(SUCCESS_TODO_UPDATE_MESSAGE));
    }

    @ApiOperation(value = "할 일 삭제", notes = "삭제할 todo id 파라미터 필요")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "AUTHORIZATION", value = "accessToken", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(getMember().getId(), id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Message(SUCCESS_TODO_DELETE_MESSAGE));
    }

    @ApiOperation(value = "할 일 단일 조회", notes = "조회할 todo id 파라미터 필요")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "AUTHORIZATION", value = "accessToken", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TodoPageRespDto> readOneTodo(@PathVariable Long id) {
        TodoPageRespDto todo = todoService.findOneTodo(getMember().getId(), id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(todo);
    }

    @ApiOperation(value = "할 일 조회", notes = "멤버id, 조회하고 싶은 할일을 페이징으로 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "AUTHORIZATION", value = "accessToken", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping()
    public ResponseEntity<Page<TodoPageRespDto>> readTodos(@RequestParam("page") Integer page,
                                                           @RequestParam("size") Integer size) {
        Page<TodoPageRespDto> todos = todoService.findTodos(getMember().getId(), page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(todos);
    }

    private static Member getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Member) authentication.getPrincipal();
    }

}
