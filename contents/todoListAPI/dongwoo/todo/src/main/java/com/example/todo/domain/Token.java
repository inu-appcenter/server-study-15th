package com.example.todo.domain;

import com.example.todo.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 리프레쉬 토큰 저장을 위한 엔티티
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Token extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "TOKEN_ID")
    private Long id;

    @Column
    private String refreshToken;

    @Column
    private Long userId;

    public Token(String refreshToken, Long userId) {
        this.refreshToken = refreshToken;
        this.userId = userId;
    }

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
}
