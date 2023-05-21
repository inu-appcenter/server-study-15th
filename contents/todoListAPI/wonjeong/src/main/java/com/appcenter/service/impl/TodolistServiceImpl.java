package com.appcenter.service.impl;

import com.appcenter.data.dto.request.TodolistRequestDTO;
import com.appcenter.data.dto.response.TodolistResponseDTO;
import com.appcenter.data.entity.Todolist;
import com.appcenter.data.repository.TodolistRepository;
import com.appcenter.service.TodolistService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TodolistServiceImpl implements TodolistService {

    // Repository 상수 선언
    private final TodolistRepository todolistRepository;
    // 오류 메세지 상수 선언
    private final String NOT_FOUND_CONTENT = "유효하지 않은 게시글 번호입니다.";

    public TodolistServiceImpl(TodolistRepository todolistRepository) {
        this.todolistRepository = todolistRepository;
    }

    @Transactional
    // 트랜젝션이 있는데 왜 Exception이 있을까.. 내가 왜 그랬을까..
    // 예외처리 수정할 때 같이 수정하기..
    public TodolistResponseDTO getContent(Long id) throws Exception {
        Todolist todolist = todolistRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONTENT));

       return new TodolistResponseDTO().updateTodolistResponse(todolist);
    }

    @Override
    public TodolistResponseDTO savedContent(TodolistRequestDTO todolistRequestDTO) {
        Todolist todolist = new Todolist();

        Todolist savedContent = todolistRepository.save(todolist.createContent(todolistRequestDTO));

        return new TodolistResponseDTO().updateTodolistResponse(savedContent);
    }

    @Override
    public TodolistResponseDTO updateContent(Long id, TodolistRequestDTO todolistRequestDTO) throws Exception {
        Todolist foundContent = todolistRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONTENT));

        Todolist updateContent = todolistRepository.save(foundContent.updateContent(id, todolistRequestDTO));

        return new TodolistResponseDTO().updateTodolistResponse(updateContent);
    }

    @Override
    public void deleteContent(Long id) throws Exception {
        try {
            todolistRepository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException(NOT_FOUND_CONTENT);
        }
    }
}
