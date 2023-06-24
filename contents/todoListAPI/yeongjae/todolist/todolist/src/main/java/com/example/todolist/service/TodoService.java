package com.example.todolist.service;

import com.example.todolist.domain.Todo;
import com.example.todolist.domain.user.User;
import com.example.todolist.exception.todo.TodoNotFoundException;
import com.example.todolist.exception.user.UserNotFoundException;
import com.example.todolist.repository.TodoRepository;
import com.example.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import static com.example.todolist.dto.taskdto.TodoRequestDto.*;
import static com.example.todolist.dto.taskdto.TodoResponseDto.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Transactional
    public TodoSaveRespDto writeTodo(TodoSaveReqDto TodoSaveReqDto, Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }
        User user = optionalUser.get();

        Todo todo = todoRepository.save(TodoSaveReqDto.changeEntity(TodoSaveReqDto,user));

        @Valid final TodoSaveRespDto todoSaveRespDto = new TodoSaveRespDto(todo);

        return todoSaveRespDto;
    }

    @Transactional
    public TodoDeleteRespDto deleteTodo(Long id, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Todo> optionalTodo = todoRepository.findById(id);

        if(optionalTodo.isEmpty() || optionalTodo.isEmpty()) {
            throw new TodoNotFoundException();
        }
        checkUserHaveTodo(optionalTodo, optionalUser);
        Todo todo = optionalTodo.get();

        todoRepository.deleteById(id);

        @Valid final TodoDeleteRespDto todoDeleteRespDto = new TodoDeleteRespDto(todo);

        return todoDeleteRespDto;
    }

    @Transactional
    public TodoEditRespDto editTodo(Long id, TodoEditRequestDto todoEditRequestDto, Long userId) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        Optional<User> optionalUser = userRepository.findById(userId);

        checkUserHaveTodo(optionalTodo, optionalUser);

        Todo todo = optionalTodo.get();

        todo.setContents(todoEditRequestDto.getContents());
        todo.setTitle(todoEditRequestDto.getTitle());
        todo.setDeadline(parseDatetime(todoEditRequestDto.getDeadline()));

        @Valid final TodoEditRespDto todoEditRespDto = new TodoEditRespDto(todo);

        return todoEditRespDto;
    }

    @Transactional(readOnly = true)
    public List<Todo> findTodoById(Long userId) {
        Optional<User> opUser = userRepository.findById(userId);
        if(opUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        return todoRepository.findByUser_id(userId);
    }

    public void checkUserHaveTodo(Optional<Todo> optionalTodo, Optional<User> optionalUser) {
        if(optionalTodo.isEmpty()) {
            throw new TodoNotFoundException();
        }
        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = optionalUser.get();
        Todo todo = optionalTodo.get();

        if(todo.getUser() != user) {
            throw new UserNotFoundException();
        }
    }
}