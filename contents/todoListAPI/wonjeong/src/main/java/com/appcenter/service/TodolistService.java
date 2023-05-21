package com.appcenter.service;

import com.appcenter.data.dto.TodolistDTO;
import com.appcenter.data.dto.response.TodolistResponseDTO;

public interface TodolistService {

    TodolistResponseDTO getContent(Long id) throws Exception;

    TodolistResponseDTO savedContent(TodolistDTO todolistDTO);

    TodolistResponseDTO updateContent(Long id, String name) throws Exception;

    void deleteContent(Long id) throws Exception;
}
