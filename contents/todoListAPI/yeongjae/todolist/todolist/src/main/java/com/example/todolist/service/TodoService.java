package com.example.todolist.service;

import com.example.todolist.domain.Todo;
import com.example.todolist.exception.todo.TodoNotFoundException;
import com.example.todolist.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import static com.example.todolist.dto.taskdto.TodoRequestDto.*;
import static com.example.todolist.dto.taskdto.TodoResponseDto.*;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    @Transactional
    public TaskSaveRespDto writeTodo(TodoSaveReqDto TodoSaveReqDto) {
        Todo todo = todoRepository.save(TodoSaveReqDto.changeEntity(TodoSaveReqDto));
        return new TaskSaveRespDto(todo);
    }

    @Transactional
    public TaskDeleteRespDto deleteTodo(Long id) {
        Optional<Todo> optionalTask = todoRepository.findById(id);

        if(optionalTask.isEmpty()) {
            throw new TodoNotFoundException();
        }
        Todo todo = optionalTask.get();

        todoRepository.deleteById(id);

        return new TaskDeleteRespDto(todo);
    }

    @Transactional
    public TaskEditRespDto editTodo(Long id, TodoEditRequestDto todoEditRequestDto) {
        Optional<Todo> optionalTask = todoRepository.findById(id);

            if (optionalTask.isPresent()) {
                Todo todo = optionalTask.get();

                todo.setContents(todoEditRequestDto.getContents());
                todo.setTitle(todoEditRequestDto.getTitle());
                todo.setDeadline(parseDatetime(todoEditRequestDto.getDeadline()));

            return new TaskEditRespDto(todo);
        } else {
            throw new TodoNotFoundException();
        }
    }

    @Transactional(readOnly = true)
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }
}