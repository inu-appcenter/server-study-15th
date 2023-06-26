package com.example.todolist.config.jwt;

public class JwtProperties {
    public static final String SECRET = "cos";
    public static final int EXPIRATION_TIME = 864000000; // 10일
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}