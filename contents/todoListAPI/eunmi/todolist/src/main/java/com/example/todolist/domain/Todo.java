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
    private LocalDateTime deadline;

    @Column(nullable = false)
    private boolean checked;

    @Builder
    public Todo(Member member, String content, LocalDateTime deadline, boolean checked) {
        setMember(member);
        this.content = content;
        this.deadline = deadline;
        this.checked = checked;
    }

    public void updateTodo(TodoReqDto todoReqDto) {
        this.content = todoReqDto.getContent();
        this.deadline = todoReqDto.getDeadline();
    }

    public boolean isCheckMember(Long newId) {
        if (newId == null) return false;
        return newId.equals(this.member.getId());
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

    public void setChecked(TodoCheckReqDto todoCheckReqDto) {
        this.checked = todoCheckReqDto.isChecked();
    }

    public TodoPageRespDto toTodoPageRespDto(Todo todo) {
        return TodoPageRespDto.builder()
                .content(todo.getContent())
                .checked(todo.isChecked())
                .build();
    }
}
