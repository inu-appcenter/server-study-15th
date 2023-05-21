package com.example.todolist.service;

import com.example.todolist.domain.Member;
import com.example.todolist.domain.Todo;
import com.example.todolist.dto.TodoCheckReqDto;
import com.example.todolist.dto.TodoPageRespDto;
import com.example.todolist.dto.TodoReqDto;
import com.example.todolist.dto.TodoRespDto;
import com.example.todolist.exception.NotFoundMemberException;
import com.example.todolist.exception.NotFoundTodoException;
import com.example.todolist.repository.MemberRepository;
import com.example.todolist.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final MemberRepository memberRepository;
    private final TodoRepository todoRepository;

    private final String NOT_FOUND_MEMBER_MESSAGE = "회원정보가 존재하지 않습니다.";
    private final String NOT_FOUND_TODO_MESSAGE = "등록된 할 일 정보를 찾을 수 없습니다.";

    @Transactional
    public Long save(Long memberId, TodoReqDto todoReqDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException(NOT_FOUND_MEMBER_MESSAGE));
        Todo todo = todoReqDto.toEntity(member, todoReqDto);
        Todo savedTodo = todoRepository.save(todo);
        return savedTodo.getId();
    }

    @Transactional
    public Long update(Long id, TodoReqDto todoReqDto) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new NotFoundTodoException(NOT_FOUND_TODO_MESSAGE));
        Todo creatTodo = todo.updateTodo(todoReqDto);
        return creatTodo.getId();
    }

    public TodoRespDto findOne(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new NotFoundTodoException(NOT_FOUND_TODO_MESSAGE));
        return TodoRespDto.builder()
                .id(todo.getId())
                .content(todo.getContent())
                .creatAt(todo.getCreatAt())
                .checked(todo.isChecked())
                .build();
    }

    public List<TodoPageRespDto> findAll(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException(NOT_FOUND_MEMBER_MESSAGE));
        List<Todo> todos = todoRepository.findAllByMember(member);
        return todos.stream().map(todo -> todo.toTodoPageRespDto(todo))
                .collect(Collectors.toList());
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    public Long updateChecked(Long id, TodoCheckReqDto todoCheckReqDto) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new NotFoundTodoException(NOT_FOUND_TODO_MESSAGE));
        Todo updateTodo = todo.setChecked(todoCheckReqDto);
        return updateTodo.getId();
    }
}
