package com.example.todolist.service;

import com.example.todolist.domain.Todo;
import com.example.todolist.domain.user.User;
import com.example.todolist.exception.CustomException;
import com.example.todolist.repository.TodoRepository;
import com.example.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import static com.example.todolist.dto.taskdto.TodoRequestDto.*;
import static com.example.todolist.dto.taskdto.TodoResponseDto.*;
import static com.example.todolist.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Transactional
    public TodoSaveRespDto writeTodo(TodoSaveReqDto TodoSaveReqDto, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException(USER_NOT_FOUND_EXCEPTION));
        Todo todo = todoRepository.save(TodoSaveReqDto.changeEntity(TodoSaveReqDto,user));

        @Valid final TodoSaveRespDto todoSaveRespDto = new TodoSaveRespDto(todo);

        return todoSaveRespDto;
    }

    @Transactional
    public TodoDeleteRespDto deleteTodo(Long id, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(USER_NOT_FOUND_EXCEPTION));
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new CustomException(TODO_NOT_FOUND_EXCEPTION));

        checkUserHaveTodo(user, todo);

        todoRepository.deleteById(id);

        @Valid final TodoDeleteRespDto todoDeleteRespDto = new TodoDeleteRespDto(todo);

        return todoDeleteRespDto;
    }

    @Transactional
    public TodoEditRespDto editTodo(Long id, TodoEditRequestDto todoEditRequestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(USER_NOT_FOUND_EXCEPTION));
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new CustomException(TODO_NOT_FOUND_EXCEPTION));

        checkUserHaveTodo(user, todo);

        todo.setContents(todoEditRequestDto.getContents());
        todo.setTitle(todoEditRequestDto.getTitle());
        todo.setDeadline(parseDatetime(todoEditRequestDto.getDeadline()));

        @Valid final TodoEditRespDto todoEditRespDto = new TodoEditRespDto(todo);

        return todoEditRespDto;
    }

    @Transactional(readOnly = true)
    public List<Todo> findTodoById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(USER_NOT_FOUND_EXCEPTION));

        return todoRepository.findByUser_id(userId);
    }

    @Transactional(readOnly = true)
    public List<Todo> findTodoByTitle(String title, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(USER_NOT_FOUND_EXCEPTION));

        return todoRepository.findByTitle(title, userId);
    }

    public void checkUserHaveTodo(User user, Todo todo) {
        if(todo.getUser() != user) {
            throw new CustomException(USER_NOT_FOUND_EXCEPTION);
        }
    }


}