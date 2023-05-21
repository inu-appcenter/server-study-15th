package com.appcenter.data.entity;

import com.appcenter.data.dto.request.TodolistRequestDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Todolist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String contents;

    // 다대일 어노테이션
    // Fetch에는 두가지 타입 존재
    // EAGER는 엔티티 조회시 연관된 엔티티를 즉시 한번에 조회
    // LAZY는 실제 사용 시점에 조회
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Builder
    public Todolist(Long id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }


    public Todolist createContent(TodolistRequestDTO todolistRequestDTO) {
        return Todolist.builder()
                .title(todolistRequestDTO.getTitle())
                .contents(todolistRequestDTO.getContents())
                .build();
    }

    public Todolist updateContent(Long id, TodolistRequestDTO todolistRequestDTO) {
        return Todolist.builder()
                .id(id)
                .title(todolistRequestDTO.getTitle())
                .contents(todolistRequestDTO.getContents())
                .build();
    }

}
