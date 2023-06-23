package com.example.todolist.domain.user;

import com.example.todolist.domain.Todo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.config.Task;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.todolist.dto.userdto.UserRequestDto.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false,length = 20)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private UserEnum role;

    @OneToMany(mappedBy = "user")
    private List<Todo> todoList = new ArrayList<>();

    public void editByDto(UserEditReqDto userEditReqDto) {
        this.name = userEditReqDto.getName();
        this.password = userEditReqDto.getPassword();
        this.email = userEditReqDto.getEmail();
    }

    @Builder
    public User(String name, String password, String email, List<Todo> taskList, UserEnum role) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.todoList = taskList;
        this.role = role;
    }
}