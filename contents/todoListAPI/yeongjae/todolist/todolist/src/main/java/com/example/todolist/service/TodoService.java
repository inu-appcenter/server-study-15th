package com.example.todolist.service;

import com.example.todolist.domain.Task;
import com.example.todolist.dto.TaskDto;
import com.example.todolist.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    @Transactional
    public void writeTodo(TaskDto taskDto) {
        todoRepository.save(taskDto.changeEntity(taskDto));
    }

    @Transactional
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    public List<Task> findAll() {
        return todoRepository.findAll();
    }
}
