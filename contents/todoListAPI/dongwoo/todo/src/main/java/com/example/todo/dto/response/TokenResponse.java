package com.example.todo.dto.response;

import com.example.todo.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {

    private String token;
    private TokenType tokenType;
}
