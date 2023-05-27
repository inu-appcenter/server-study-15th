package com.example.todo.domain;

import com.example.todo.common.domain.BaseTimeEntity;
import com.example.todo.dto.request.UpdateUserRequest;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Todo> todoList = new ArrayList<>();

    @Builder
    public User(String name, String email, String password) {
        this.name = name;
        this. email = email;
        this.password = password;
    }

    public void update(UpdateUserRequest request) {
        this.name = request.getName();
        this.email = request.getEmail();
    }
}
