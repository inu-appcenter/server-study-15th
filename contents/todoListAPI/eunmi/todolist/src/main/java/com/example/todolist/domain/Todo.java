package com.example.todolist.domain;

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
        setMember(member);
        this.content = todoReqDto.getContent();
        this.creatAt = todoReqDto.getCreatAt();
        this.checked = todoReqDto.isChecked();
        return this;
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
}
