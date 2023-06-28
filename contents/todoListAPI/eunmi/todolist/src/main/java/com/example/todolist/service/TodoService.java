package com.example.todolist.service;

import com.example.todolist.domain.Member;
import com.example.todolist.domain.Todo;
import com.example.todolist.dto.TodoCheckReqDto;
import com.example.todolist.dto.TodoPageRespDto;
import com.example.todolist.dto.TodoReqDto;
import com.example.todolist.exception.BadRequestException;
import com.example.todolist.exception.NotFoundException;
import com.example.todolist.repository.MemberRepository;
import com.example.todolist.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {
    private final MemberRepository memberRepository;
    private final TodoRepository todoRepository;

    private final String NOT_FOUND_MEMBER_MESSAGE = "회원정보가 존재하지 않습니다.";
    private final String NOT_FOUND_TODO_MESSAGE = "등록된 할 일 정보를 찾을 수 없습니다.";
    private final String NOT_OWN_TODO_MESSAGE = "현재 회원의 할 일 정보를 찾을 수 없습니다.";
    private final String SUCCESS_TODO_SAVE_MESSAGE = "할 일 등록 완료";
    private final String SUCCESS_TODO_UPDATE_MESSAGE = "할 일 수정 완료";
    private final String SUCCESS_TODO_DELETE_MESSAGE = "할 일 삭제 완료";
    private final String BAD_REQUEST_MEMBER_MESSAGE = "해당 회원의 정보가 아닙니다. 다시 입력해주세요.";

    @Transactional
    public void save(Long memberId, TodoReqDto todoReqDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MEMBER_MESSAGE));
        Todo todo = todoReqDto.toEntity(member, todoReqDto);
        todoRepository.save(todo);
        log.info(SUCCESS_TODO_SAVE_MESSAGE);
    }

    @Transactional
    public void update(Long memberId, Long id, TodoReqDto todoReqDto) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_TODO_MESSAGE));
        isCheckMember(memberId, todo);  // 해당 회원의 todo가 맞는지 검사
        todo.updateTodo(todoReqDto);
        log.info(SUCCESS_TODO_UPDATE_MESSAGE);
    }

    public Page<TodoPageRespDto> findTodos(Long memberId, int page, int size) {
        Member member = memberRepository.findById(memberId).orElseThrow(()
                -> new NotFoundException(NOT_FOUND_MEMBER_MESSAGE));
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "deadline"));
        Page<Todo> pageByMember = todoRepository.findPageByMember(member, pageRequest);
        return pageByMember.map(todo -> todo.toTodoPageRespDto(todo));
    }

    @Transactional
    public TodoPageRespDto findOneTodo(Long memberId, Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_TODO_MESSAGE));
        isCheckMember(memberId, todo);
        return todo.toTodoPageRespDto(todo);
    }

    public void deleteTodo(Long memberId, Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_TODO_MESSAGE));
        isCheckMember(memberId, todo);
        todoRepository.deleteById(id);
        log.info(SUCCESS_TODO_DELETE_MESSAGE);
    }

    @Transactional
    public void updateChecked(Long memberId, Long id, TodoCheckReqDto todoCheckReqDto) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_TODO_MESSAGE));
        isCheckMember(memberId, todo);
        todo.setChecked(todoCheckReqDto);
    }

    public void isCheckMember(Long userId, Todo todo) {
        if (!todo.isCheckMember(userId))
            throw new BadRequestException(BAD_REQUEST_MEMBER_MESSAGE);
    }
}
