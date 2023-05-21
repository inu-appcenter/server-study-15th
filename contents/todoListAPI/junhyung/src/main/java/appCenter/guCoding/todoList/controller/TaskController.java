package appCenter.guCoding.todoList.controller;

import appCenter.guCoding.todoList.dto.ResponseDto;
import appCenter.guCoding.todoList.dto.task.TaskReqDto.TaskEditReqDto;
import appCenter.guCoding.todoList.dto.task.TaskReqDto.TaskSaveReqDto;
import appCenter.guCoding.todoList.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static appCenter.guCoding.todoList.dto.task.TaskRespDto.*;

@RequiredArgsConstructor
@RequestMapping("/api/tasks")
@RestController
public class TaskController {
    // jwt login 이후 수정
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<?> saveTodo(@RequestBody @Valid TaskSaveReqDto taskSaveReqDto, BindingResult bindingResult) {
        TaskSaveRespDto taskSaveRespDto = taskService.할일저장(taskSaveReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1,"할 일 추가 성공", taskSaveRespDto), HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<?> checkTodo(@RequestParam(value = "title",required = false) String title) {
        if (title == null) {
            TaskListRespDto taskListRespDto = taskService.할일목록보기();
            return new ResponseEntity<>(new ResponseDto<>(1, "할 일 목록 조회 성공", taskListRespDto), HttpStatus.OK);
        }

        TaskDtoTitle taskDtoTitle = taskService.할일_title_조회(title);
        return new ResponseEntity<>(new ResponseDto<>(1, "할 일 조회 성공", taskDtoTitle), HttpStatus.OK);

    }

    @PutMapping("/{id}") //
    public ResponseEntity<?> editTodo(@RequestBody @Valid TaskEditReqDto taskEditReqDto, BindingResult bindingResult, @PathVariable Long id) {
        TaskEditRespDto taskEditRespDto = taskService.할일수정(id, taskEditReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "할 일 수정 성공", taskEditRespDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> checkTodoWithId(@PathVariable Long id) {
        TaskDtoId taskDtoId = taskService.할일_id_조회(id);
        return new ResponseEntity<>(new ResponseDto<>(1, "할 일 조회 성공", taskDtoId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id) {
        taskService.할일삭제(id);
        return new ResponseEntity<>(new ResponseDto<>(1, "할 일 삭제 성공", null), HttpStatus.NO_CONTENT);
    }





}
