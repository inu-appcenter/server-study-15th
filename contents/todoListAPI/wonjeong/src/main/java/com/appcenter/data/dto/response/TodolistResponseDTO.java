package com.appcenter.data.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TodolistResponseDTO {

    private Long id;
    private String title;
    private String contents;

}
