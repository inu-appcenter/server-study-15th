package com.example.todo.dto.data;

import com.example.todo.domain.User;
import lombok.Getter;

@Getter
public class UserData {

    private Long userid;

    public UserData(User user) {
        this.userid = user.getId();
    }
}
