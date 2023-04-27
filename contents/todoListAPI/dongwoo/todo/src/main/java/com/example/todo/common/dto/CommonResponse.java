package com.example.todo.common.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonResponse {
    private String message;

    @Builder
    public CommonResponse(String message) {
        this.message = message;
    }
}
