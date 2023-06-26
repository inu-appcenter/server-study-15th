package com.example.todolist.domain;


import com.example.todolist.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Todo {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 20)
    private String title;
    private String contents;

    @Column(nullable = false)
    private Boolean isCompleted;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Todo(Long id, String title, String contents, Boolean isCompleted, User user, LocalDateTime deadline) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.isCompleted = isCompleted;
        this.user = user;
        this.deadline = deadline;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDeadline(LocalDateTime parseDatetime) {
        this.deadline = parseDatetime;
    }
}