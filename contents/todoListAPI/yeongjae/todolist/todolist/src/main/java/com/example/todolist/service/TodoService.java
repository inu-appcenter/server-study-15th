package com.example.todolist.service;

import com.example.todolist.domain.Task;
import com.example.todolist.exception.TodoNotFoundException;
import com.example.todolist.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.example.todolist.dto.taskdto.TaskRequestDto.*;
import static com.example.todolist.dto.taskdto.TaskResponseDto.*;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    @Transactional
    public TaskSaveRespDto writeTodo(TaskSaveReqDto TaskSaveReqDto) {
        Task task = todoRepository.save(TaskSaveReqDto.changeEntity(TaskSaveReqDto));
        return new TaskSaveRespDto(task);
    }

    @Transactional
    public TaskDeleteRespDto deleteTodo(Long id) {
        Optional<Task> optionalTask = todoRepository.findById(id);

        if(optionalTask.isEmpty()) {
            throw new TodoNotFoundException();
        }
        Task task = optionalTask.get();

        todoRepository.deleteById(id);

        return new TaskDeleteRespDto(task);
    }

    @Transactional
    public TaskEditRespDto editTodo(Long id, TaskEditRequestDto taskEditRequestDto) {
        Optional<Task> optionalTask = todoRepository.findById(id);

        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();

            task.setContents(taskEditRequestDto.getContents());
            task.setTitle(taskEditRequestDto.getTitle());
            task.setDeadline(parseDatetime(taskEditRequestDto.getDeadline()));

            return new TaskEditRespDto(task);
        } else {
            throw new TodoNotFoundException();
        }
    }

    @Transactional
    public List<Task> findAll() {
        return todoRepository.findAll();
    }
}