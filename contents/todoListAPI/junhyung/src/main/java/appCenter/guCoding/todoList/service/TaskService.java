package appCenter.guCoding.todoList.service;

import appCenter.guCoding.todoList.domain.task.Task;
import appCenter.guCoding.todoList.domain.task.TaskRepository;
import appCenter.guCoding.todoList.dto.task.TaskReqDto.TaskEditReqDto;
import appCenter.guCoding.todoList.dto.task.TaskReqDto.TaskSaveReqDto;
import appCenter.guCoding.todoList.dto.task.TaskRespDto;
import appCenter.guCoding.todoList.dto.task.TaskRespDto.*;
import appCenter.guCoding.todoList.handler.ex.CustomApiException;
import appCenter.guCoding.todoList.handler.ex.CustomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskSaveRespDto 할일저장(TaskSaveReqDto taskSaveReqDto) {
        Optional<Task> taskOP = taskRepository.findByTitle(taskSaveReqDto.getTitle());
        if (taskOP.isPresent()) {
            throw new CustomApiException("동일한 할 일이 존재합니다.");
        }
        Task taskPS = taskRepository.save(taskSaveReqDto.toEntity());
        return new TaskSaveRespDto(taskPS);
    }

    public TaskListRespDto 할일목록보기() {
        List<Task> taskList = taskRepository.findAll();
        return new TaskListRespDto(taskList);
    }

    public TaskEditRespDto 할일수정(Long id, TaskEditReqDto taskEditReqDto) {
        Optional<Task> taskOp = taskRepository.findById(id);
        if (taskOp.isEmpty()) {
            throw new CustomNotFoundException("해당 ID에 해당하는 할 일이 없습니다.");
        }
        Task taskPS = taskOp.get();
        taskPS.changeField(taskEditReqDto);
        return new TaskEditRespDto(taskPS);

    }

    public TaskDtoId 할일_id_조회(Long id) {
        Optional<Task> taskOp = taskRepository.findById(id);
        if (taskOp.isEmpty()) {
            throw new CustomNotFoundException("해당 ID에 해당하는 할 일이 없습니다.");
        }
        return new TaskDtoId(taskOp.get());
    }

    public void 할일삭제(Long id) {
        taskRepository.deleteById(id);
    }

    public TaskDtoTitle 할일_title_조회(String title) {
        Optional<Task> taskOP = taskRepository.findByTitle(title);
        if (taskOP.isEmpty()) {
            throw new CustomNotFoundException("해당 제목에 해당하는 할 일이 없습니다.");
        }
        return new TaskDtoTitle(taskOP.get());
    }
}
