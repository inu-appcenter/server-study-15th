package com.appcenter.data.entity;

import com.appcenter.data.dto.request.TodolistRequestDTO;
import com.appcenter.data.dto.response.TodolistResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Todolist extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String contents;

    // 다대일 어노테이션
    // Fetch에는 두가지 타입 존재
    // EAGER는 엔티티 조회시 연관된 엔티티를 즉시 한번에 조회
    // LAZY는 실제 사용 시점에 조회
    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Builder
    public Todolist(Long id, String title, String contents, Member member) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.member = member;
    }


    public Todolist createContent(Member member, TodolistRequestDTO todolistRequestDTO) {
        return Todolist.builder()
                .member(member)
                .title(todolistRequestDTO.getTitle())
                .contents(todolistRequestDTO.getContents())
                .build();
    }

    public void updateContent(TodolistRequestDTO todolistRequestDTO) {
        this.title = todolistRequestDTO.getTitle();
        this.contents = todolistRequestDTO.getContents();
    }

    public TodolistResponseDTO toTodolistResponseDTO(Todolist todolist) {
        TodolistResponseDTO todolistResponseDTO = new TodolistResponseDTO();
        todolistResponseDTO.setTodolistResponse(todolist);
        return todolistResponseDTO;
    }

}
