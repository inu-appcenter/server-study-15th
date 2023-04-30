package com.example.todolist.domain;


import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Todo {

    @Id @GeneratedValue
    private Long id;

    private String day;
    private String haveTodo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
