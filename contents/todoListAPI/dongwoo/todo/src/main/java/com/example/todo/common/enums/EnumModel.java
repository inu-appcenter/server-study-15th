package com.example.todo.common.enums;

/**
 * enum 형을 다형성을 이용한 유연한 개발을 하기 위해 EnumModel 인터페이스 정의
 */
public interface EnumModel {
    String getKey();

    String getValue();
}
