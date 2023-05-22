package com.appcenter.service;

import com.appcenter.data.dto.request.TodolistRequestDTO;
import com.appcenter.data.dto.response.TodolistResponseDTO;

public interface TodolistService {

    TodolistResponseDTO getContent(Long id) throws Exception;

    TodolistResponseDTO savedContent(Long id, TodolistRequestDTO todolistRequestDTO) throws  Exception;

    TodolistResponseDTO updateContent(Long id, TodolistRequestDTO todolistRequestDTO) throws Exception;

    void deleteContent(Long id) throws Exception;
}
