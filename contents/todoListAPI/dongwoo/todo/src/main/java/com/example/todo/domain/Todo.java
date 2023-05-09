package com.example.todo.domain;

import com.example.todo.common.domain.BaseTimeEntity;
import com.example.todo.dto.request.UpdateTodoRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Todo extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "TODO_ID")
    private Long id;

    @Column
    private String contents;

    @Column
    private Date deadLine;

    @Column
    private Boolean finished;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Builder
    public Todo(String contents, Date deadLine) {
        this.contents = contents;
        this.deadLine = deadLine;
        this.finished = false;
    }

    public void mappingUser(User user) {
        this.user = user;
        user.getTodoList().add(this);
    }

    public void update(UpdateTodoRequest request) {
        this.contents = request.getContents();
    }

    public void doTodo() {
        this.finished = true;
    }
}
