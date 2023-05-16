package com.appcenter.service;

import com.appcenter.data.dto.TodolistDTO;
import com.appcenter.data.dto.response.TodolistResponseDTO;

public interface TodolistService {

    TodolistResponseDTO getContent(Long number);

    TodolistResponseDTO savedContent(TodolistDTO todolistDTO);

    TodolistResponseDTO updateContent(Long number, String name) throws Exception;

    void deleteContent(Long number) throws Exception;
}
