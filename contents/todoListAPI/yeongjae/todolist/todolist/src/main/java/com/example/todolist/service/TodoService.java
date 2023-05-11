package com.example.todolist.service;

import com.example.todolist.domain.Task;
import com.example.todolist.dto.TaskDto;
import com.example.todolist.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;


    public void writeTodo(TaskDto taskDto) {
        todoRepository.save(taskDto.changeEntity(taskDto));
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    public void editTodo(Long id, TaskDto taskDto) throws Exception {
        Optional<Task> optionalTask = todoRepository.findById(id);

        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();

            task.setContents(taskDto.getContents());
            task.setTitle(taskDto.getTitle());
            task.setDeadline(taskDto.parseDatetime(taskDto.getDeadline()));
        } else {
            throw new Exception("id 로 조회되는 정보가 없습니다");
        }
    }

    public List<Task> findAll() {
        return todoRepository.findAll();
    }
}