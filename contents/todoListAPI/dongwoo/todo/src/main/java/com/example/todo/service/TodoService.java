package com.example.todo.service;

import com.example.todo.common.exception.todo.TodoNotFoundException;
import com.example.todo.domain.Todo;
import com.example.todo.dto.data.TodoListData;
import com.example.todo.dto.request.CreateTodoRequest;
import com.example.todo.dto.request.UpdateTodoRequest;
import com.example.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;

    public void createTodo(CreateTodoRequest request) {

        Todo todo = Todo.builder()
                .contents(request.getContents())
                .deadLine(request.getDeadLine())
                .build();

        todoRepository.save(todo);

    }

    public void deleteTodo(Long todoId) {

        todoRepository.findById(todoId).orElseThrow(TodoNotFoundException::new);

        todoRepository.deleteById(todoId);
    }

    public void updateTodo(Long todoId, UpdateTodoRequest request) {

        Todo todo = todoRepository.findById(todoId).orElseThrow(TodoNotFoundException::new);

        todo.builder().contents(request.getContents());

        todo.update(request);
    }

    public Slice<TodoListData> getTodoList(int page, int size) {


        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        Slice<Todo> todoSlice = todoRepository.findSliceBy(pageRequest);

        Slice<TodoListData> todoListData = todoSlice.map(todo -> {
            todo.getContents(); //프록시 처리된 user 엔티티 가져오기 위함
            return new TodoListData(todo);
        });

        return todoListData;
    }

    public void doTodo(Long todoId) {

        Todo todo = todoRepository.findById(todoId).orElseThrow(TodoNotFoundException::new);

        todo.doTodo();
    }
}
