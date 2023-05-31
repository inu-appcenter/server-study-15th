package com.example.todo.service;

import com.example.todo.common.exception.todo.TodoNotFoundException;
import com.example.todo.common.exception.user.UserNotAccessRightException;
import com.example.todo.common.exception.user.UserNotFoundException;
import com.example.todo.domain.Todo;
import com.example.todo.domain.User;
import com.example.todo.dto.data.TodoListData;
import com.example.todo.dto.request.CreateTodoRequest;
import com.example.todo.dto.request.UpdateTodoRequest;
import com.example.todo.repository.TodoRepository;
import com.example.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public void createTodo(CreateTodoRequest request, Long userId) {

        User findUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Todo todo = Todo.builder()
                .user(findUser)
                .contents(request.getContents())
                .deadLine(request.getDeadLine())
                .build();

        todoRepository.save(todo);

    }

    public void deleteTodo(Long todoId, Long userId) {

        Todo todo = todoRepository.findById(todoId).orElseThrow(TodoNotFoundException::new);

        isUserTodo(todo, userId);

        todoRepository.deleteById(todoId);
    }

    public void updateTodo(Long todoId, Long userId, UpdateTodoRequest request) {

        Todo todo = todoRepository.findById(todoId).orElseThrow(TodoNotFoundException::new);

        isUserTodo(todo, userId);

        todo.builder().contents(request.getContents());

        todo.update(request);
    }

    public Slice<TodoListData> getTodoList(int page, int size, Long userId) {

        User findUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        Slice<Todo> todoSlice = todoRepository.findSliceByUser(pageRequest, findUser);

        Slice<TodoListData> todoListData = todoSlice.map(todo -> {
            return new TodoListData(todo);
        });

        return todoListData;
    }

    public void doTodo(Long todoId, Long userId) {

        Todo todo = todoRepository.findById(todoId).orElseThrow(TodoNotFoundException::new);

        isUserTodo(todo, userId);

        todo.doTodo();
    }

    public void isUserTodo(Todo todo, Long userId) {

        if(!todo.isUsersTodo(userId)) {
            throw new UserNotAccessRightException();
        }

    }

}
