package com.example.todolist.domain;

import com.example.todolist.dto.TodoCheckReqDto;
import com.example.todolist.dto.TodoPageRespDto;
import com.example.todolist.dto.TodoReqDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false, length = 20)
    private String content;

    @Column(nullable = false)
    private LocalDateTime creatAt;

    @Column(nullable = false)
    private boolean checked;

    @Builder
    public Todo(Member member, String content, LocalDateTime creatAt, boolean checked) {
        setMember(member);
        this.content = content;
        this.creatAt = creatAt;
        this.checked = checked;
    }

    public Todo updateTodo(TodoReqDto todoReqDto) {
        return Todo.builder()
                .member(this.member)
                .content(todoReqDto.getContent())
                .checked(todoReqDto.isChecked())
                .build();
    }

    /**
     * 연관관계 메서드
     * @param member
     */
    public void setMember(Member member) {
        if (this.member != null) {
            member.getTodos().remove(this);
        }
        this.member = member;
        member.getTodos().add(this);
    }

    public Todo setChecked(TodoCheckReqDto todoCheckReqDto) {
        return Todo.builder()
                .member(this.member)
                .content(this.content)
                .creatAt(this.creatAt)
                .checked(todoCheckReqDto.isChecked())
                .build();
    }

    public TodoPageRespDto toTodoPageRespDto(Todo todo) {
        return TodoPageRespDto.builder()
                .content(todo.getContent())
                .checked(todo.isChecked())
                .build();
    }
}
