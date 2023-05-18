package com.appcenter.service.impl;

import com.appcenter.data.dto.TodolistDTO;
import com.appcenter.data.dto.response.TodolistResponseDTO;
import com.appcenter.data.entity.Todolist;
import com.appcenter.data.repository.TodolistRepository;
import com.appcenter.service.TodolistService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodolistServiceImpl implements TodolistService {

    // Repository 상수 선언
    private final TodolistRepository todolistRepository;
    // 오류 메세지 상수 선언
    private final String NOT_FOUND_CONTENT = "유효하지 않은 게시글 번호입니다.";

    @Autowired
    public TodolistServiceImpl(TodolistRepository todolistRepository) {
        this.todolistRepository = todolistRepository;
    }

    @Override
    public TodolistResponseDTO getContent(Long id) {
        Todolist todolist = todolistRepository.findById(id).get();

        TodolistResponseDTO todolistResponseDTO = new TodolistResponseDTO();

        todolistResponseDTO.setId(todolist.getId());
        todolistResponseDTO.setTitle(todolist.getTitle());
        todolistResponseDTO.setContents(todolist.getContents());

        return todolistResponseDTO;
    }

    @Override
    public TodolistResponseDTO savedContent(TodolistDTO todolistDTO) {
        Todolist todolist = new Todolist();
        todolist.setTitle(todolistDTO.getTitle());
        todolist.setContents(todolistDTO.getContents());
        todolist.setMember(todolist.getMember());

        Todolist savedMember = todolistRepository.save(todolist);

        TodolistResponseDTO todolistResponseDTO = new TodolistResponseDTO();
        todolistResponseDTO.setId(savedMember.getId());
        todolistResponseDTO.setTitle(savedMember.getTitle());
        todolistResponseDTO.setContents(savedMember.getContents());

        return todolistResponseDTO;
    }

    @Override
    public TodolistResponseDTO updateContent(Long id, String name) throws Exception {
        return null;
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
