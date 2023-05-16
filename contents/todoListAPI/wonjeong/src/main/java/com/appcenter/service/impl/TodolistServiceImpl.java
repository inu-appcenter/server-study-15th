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
    public TodolistResponseDTO getContent(Long number) {
        Todolist todolist = todolistRepository.findById(number).get();

        TodolistResponseDTO todolistResponseDTO = new TodolistResponseDTO();

        todolistResponseDTO.setNumber(todolist.getNumber());
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
        todolistResponseDTO.setNumber(savedMember.getNumber());
        todolistResponseDTO.setTitle(savedMember.getTitle());
        todolistResponseDTO.setContents(savedMember.getContents());

        return todolistResponseDTO;
    }

    @Override
    public TodolistResponseDTO updateContent(Long number, String name) throws Exception {
        return null;
    }

    @Override
    public void deleteContent(Long number) throws Exception {
        try {
            todolistRepository.deleteById(number);
        } catch (Exception e) {
            throw new NotFoundException(NOT_FOUND_CONTENT);
        }
    }
}
