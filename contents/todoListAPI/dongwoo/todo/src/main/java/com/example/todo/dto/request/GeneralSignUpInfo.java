package com.example.todo.dto.request;

import lombok.Getter;

@Getter
public class GeneralSignUpInfo {
    private String name;
    private String email;
    private String password;

    public GeneralSignUpInfo(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
