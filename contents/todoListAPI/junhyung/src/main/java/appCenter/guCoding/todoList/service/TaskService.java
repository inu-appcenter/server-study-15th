package appCenter.guCoding.todoList.service;

import appCenter.guCoding.todoList.domain.task.Task;
import appCenter.guCoding.todoList.domain.task.TaskRepository;
import appCenter.guCoding.todoList.domain.user.User;
import appCenter.guCoding.todoList.domain.user.UserRepository;
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
// 리팩토링 : orElseThrow , readOnly 추가
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    // 리팩토링 : 유저아이디를 받아서,Task Entity 생성 시, user 추가
    public TaskSaveRespDto 할일저장(TaskSaveReqDto taskSaveReqDto, Long userId) {
        User userPS = userRepository.findById(userId).orElseThrow(() -> new CustomNotFoundException("유저를 찾을 수 없습니다."));
        Optional<Task> taskOP = taskRepository.findByTitle(taskSaveReqDto.getTitle());
        if (taskOP.isPresent()) {
            throw new CustomApiException("동일한 제목의 할 일이 존재합니다.");
        }

        Task taskPS = taskRepository.save(taskSaveReqDto.toEntity(userPS));
        return new TaskSaveRespDto(taskPS);
    }

    @Transactional(readOnly = true) // taskRepository.findByUser_id 추가
    public TaskListRespDto 할일목록보기(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new CustomNotFoundException("유저를 찾을 수 없습니다."));
        List<Task> taskListPS = taskRepository.findByUser_id(userId);
        return new TaskListRespDto(taskListPS);
    }

    // 리팩토링 : 소유자 확인 메서드 checkOwner 추가
    public TaskEditRespDto 할일수정(Long id, TaskEditReqDto taskEditReqDto, Long userId) {
        Task taskPS = taskRepository.findById(id).orElseThrow(() -> new CustomNotFoundException("해당 id 에 해당하는 할 일이 없습니다."));
        taskPS.checkOwner(userId);
        taskPS.changeField(taskEditReqDto);
        return new TaskEditRespDto(taskPS);

    }

    @Transactional(readOnly = true)
    public TaskDtoId 할일_id_조회(Long id) {
        Task taskPS = taskRepository.findById(id).orElseThrow(() -> new CustomNotFoundException("해당 id 에 해당하는 할 일이 없습니다."));
        return new TaskDtoId(taskPS);
    }

    // 본인계좌 확인하는 메서드 추가
    public void 할일삭제(Long id, Long userId) {
        Task taskPS = taskRepository.findById(id).orElseThrow(() -> new CustomNotFoundException("해당 id 에 해당하는 할 일이 없습니다."));
        taskPS.checkOwner(userId);
        taskRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public TaskDtoTitle 할일_title_조회(String title) {
        Optional<Task> taskOP = taskRepository.findByTitle(title);
        if (taskOP.isEmpty()) {
            throw new CustomNotFoundException("해당 제목에 해당하는 할 일이 없습니다.");
        }
        return new TaskDtoTitle(taskOP.get());
    }
}
