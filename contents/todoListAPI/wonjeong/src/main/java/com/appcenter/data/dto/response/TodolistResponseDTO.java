package com.appcenter.data.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodolistResponseDTO {

    private Long number;
    private String title;
    private String contents;

    public TodolistResponseDTO() {

    }

    public TodolistResponseDTO(Long number, String title, String contents) {
        this.number = number;
        this.title = title;
        this.contents = contents;
    }

}
