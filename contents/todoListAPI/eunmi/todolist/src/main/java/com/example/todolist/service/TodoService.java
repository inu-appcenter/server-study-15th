package com.example.todolist.service;

import com.example.todolist.domain.Member;
import com.example.todolist.domain.Todo;
import com.example.todolist.dto.TodoCheckReqDto;
import com.example.todolist.dto.TodoPageRespDto;
import com.example.todolist.dto.TodoReqDto;
import com.example.todolist.exception.NotFoundMemberException;
import com.example.todolist.exception.NotFoundTodoException;
import com.example.todolist.repository.MemberRepository;
import com.example.todolist.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final MemberRepository memberRepository;
    private final TodoRepository todoRepository;

    private final String NOT_FOUND_MEMBER_MESSAGE = "회원정보가 존재하지 않습니다.";
    private final String NOT_FOUND_TODO_MESSAGE = "등록된 할 일 정보를 찾을 수 없습니다.";

    @Transactional
    public void save(Long memberId, TodoReqDto todoReqDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException(NOT_FOUND_MEMBER_MESSAGE));
        Todo todo = todoReqDto.toEntity(member, todoReqDto);
        Todo savedTodo = todoRepository.save(todo);
    }

    @Transactional
    public void update(Long id, TodoReqDto todoReqDto) {
        //
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new NotFoundTodoException(NOT_FOUND_TODO_MESSAGE));
        Todo creatTodo = todo.updateTodo(todoReqDto);
    }

    public Page<TodoPageRespDto> findTodos(Long memberId, int page, int size) {
        Member member = memberRepository.findById(memberId).orElseThrow(()
                -> new NotFoundMemberException(NOT_FOUND_MEMBER_MESSAGE));
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate"));
        Page<Todo> pageByMember = todoRepository.findPageByMember(member, pageRequest);
        return pageByMember.map(todo -> todo.toTodoPageRespDto(todo));
    }

    @Transactional
    public TodoPageRespDto findOneTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException(NOT_FOUND_TODO_MESSAGE));
        return todo.toTodoPageRespDto(todo);
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    @Transactional
    public void updateChecked(Long id, TodoCheckReqDto todoCheckReqDto) {
        Todo todo = todoRepository.findById(id).orElseThrow(()
                -> new NotFoundTodoException(NOT_FOUND_TODO_MESSAGE));
        Todo updateTodo = todo.setChecked(todoCheckReqDto);
    }
}
