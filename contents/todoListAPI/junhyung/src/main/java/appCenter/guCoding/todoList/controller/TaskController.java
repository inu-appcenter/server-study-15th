package appCenter.guCoding.todoList.controller;

import appCenter.guCoding.todoList.config.auth.LoginUser;
import appCenter.guCoding.todoList.dto.ResponseDto;
import appCenter.guCoding.todoList.dto.task.TaskReqDto.TaskEditReqDto;
import appCenter.guCoding.todoList.dto.task.TaskReqDto.TaskSaveReqDto;
import appCenter.guCoding.todoList.dto.user.UserRespDto;
import appCenter.guCoding.todoList.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static appCenter.guCoding.todoList.dto.task.TaskRespDto.*;

@RequiredArgsConstructor
@RequestMapping("/api/tasks")
@RestController
public class TaskController {
    private final TaskService taskService;

    // @AuthenticationPrincipal : Authentication 객체 안에있는 UserDetails 는
    // 세션안에 SecurityContextHolder 에 있기 때문에 꺼내기 힘듬, 그래서 이 애노테이션으로 User 객체 를 편히 찾음

    @Operation(summary = "할 일 추가", description = "바디에 {title, description, deadline(yyyy-mm-dd)} 을 json 형식으로 보내주세요")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "할 일 추가 성공", content = @Content(schema = @Schema(implementation = TaskSaveRespDto.class))),
            @ApiResponse(responseCode = "400", description = "동일한 제목의 할 일이 존재합니다.")
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 200,message = "ok",response = TaskSaveRespDto.class)
    )
    @PostMapping
    public ResponseEntity<?> saveTodo(@RequestBody @Valid TaskSaveReqDto taskSaveReqDto, BindingResult bindingResult,
                                      @AuthenticationPrincipal LoginUser loginUser) {
        TaskSaveRespDto taskSaveRespDto = taskService.할일저장(taskSaveReqDto, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1,"할 일 추가 성공", taskSaveRespDto), HttpStatus.CREATED);
    }

    @Operation(summary = "할 일 전체 조회 및 title 별 조회", description = "요청 파라미터(없으면 전체조회)에 title 을 보내주세요")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "할 일 목록 성공", content = @Content(schema = @Schema(implementation = TaskListRespDto.class))),
            @ApiResponse(responseCode = "208 ", description = "할 일 조회 성공", content = @Content(schema = @Schema(implementation = TaskTitleRespDto.class))),
            @ApiResponse(responseCode = "404", description = "해당 제목에 해당하는 할 일이 없습니다."),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니다.")
    })
    @io.swagger.annotations.ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200,message = "ok",response = TaskListRespDto.class),
            @io.swagger.annotations.ApiResponse(code = 200,message = "ok",response = TaskTitleRespDto.class)
    })
    @GetMapping
    public ResponseEntity<?> checkTodo(@RequestParam(value = "title",required = false) String title,
                                       @AuthenticationPrincipal LoginUser loginUser) {
        if (title == null) {
            TaskListRespDto taskListRespDto = taskService.할일목록보기(loginUser.getUser().getId());
            return new ResponseEntity<>(new ResponseDto<>(1, "할 일 목록 조회 성공", taskListRespDto), HttpStatus.OK);
        }

        TaskTitleRespDto taskTitleRespDto = taskService.할일_title_조회(title);
        return new ResponseEntity<>(new ResponseDto<>(1, "할 일 조회 성공", taskTitleRespDto), HttpStatus.OK);

    }

    @Operation(summary = "할 일 수정", description = "경로변수에 id 값을, 바디에 {title, description, deadline(yyyy-mm-dd), isCompleted(boolean)} 을 json 형식으로 보내주세요")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "할 일 수정 성공", content = @Content(schema = @Schema(implementation = TaskEditRespDto.class))),
            @ApiResponse(responseCode = "404", description = "해당 id 에 해당하는 할 일이 없습니다.")
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 200,message = "ok,",response = TaskEditRespDto.class)
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> editTodo(@RequestBody @Valid TaskEditReqDto taskEditReqDto, BindingResult bindingResult, @PathVariable Long id,
                                      @AuthenticationPrincipal LoginUser loginUser) {
        TaskEditRespDto taskEditRespDto = taskService.할일수정(id, taskEditReqDto, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "할 일 수정 성공", taskEditRespDto), HttpStatus.OK);
    }

    @Operation(summary = "할 일 id 별 조회", description = "경로변수에 id 값을 보내주세요")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "할 일 조회 성공", content = @Content(schema = @Schema(implementation = TaskIdRespDto.class))),
            @ApiResponse(responseCode = "404", description = "해당 id 에 해당하는 할 일이 없습니다.")
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 200,message = "ok,",response = TaskIdRespDto.class)
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> checkTodoWithId(@PathVariable Long id) {
        TaskIdRespDto taskIdRespDto = taskService.할일_id_조회(id);
        return new ResponseEntity<>(new ResponseDto<>(1, "할 일 조회 성공", taskIdRespDto), HttpStatus.OK);
    }

    @Operation(summary = "할 일 id 별 삭제", description = "경로변수에 id 값을 보내주세요")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "할 일 삭제 성공", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "해당 id 에 해당하는 할 일이 없습니다."),
            @ApiResponse(responseCode = "400", description = "할 일 소유자가 아닙니다."),
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 204,message = "ok,",response = ResponseDto.class)
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id, @AuthenticationPrincipal LoginUser loginUser) {
        taskService.할일삭제(id, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "할 일 삭제 성공", null), HttpStatus.NO_CONTENT);
    }





}
