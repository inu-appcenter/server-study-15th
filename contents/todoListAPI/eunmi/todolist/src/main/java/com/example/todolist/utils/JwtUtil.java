package com.example.todolist.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    public static String createJwt(String userName, String secretKey, Long expiredMs) {
        Claims claims = Jwts.claims();  // claims : 원하는 정보를 담아 둘 수 있음
        claims.put("userName", userName);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))  // 현재 시간을 담아야함
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)   // 해당 알고리즘과 시크릿 킷으로 sign 됨
                .compact();

    }
}