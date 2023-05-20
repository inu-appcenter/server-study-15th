package com.example.todo.common.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class CommonResponse {
    private String message;

    @Builder
    public CommonResponse(String message) {
        this.message = message;
    }
}
