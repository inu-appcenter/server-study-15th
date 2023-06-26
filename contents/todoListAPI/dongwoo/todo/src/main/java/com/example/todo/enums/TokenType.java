package com.example.todo.enums;

import com.example.todo.common.enums.EnumModel;

public enum TokenType implements EnumModel {
    REFRESH("refresh"),
    ACCESS("access");

    private String value;

    TokenType(String value) {
        this.value = value;
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public String getValue() {
        return null;
    }
}
