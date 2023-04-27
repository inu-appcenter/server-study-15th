package com.example.todo.domain;

import com.example.todo.common.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Todo> todoList = new ArrayList<>();

    @Builder
    public User(Long id, String email, String password) {
        this.id = id;
        this. email = email;
        this.password = password;
    }

}
