package com.example.todolist.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

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
    public Task(Long id, String title, String contents, Boolean isCompleted, User user, LocalDateTime deadline) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.isCompleted = isCompleted;
        this.user = user;
        this.deadline = deadline;
    }
}