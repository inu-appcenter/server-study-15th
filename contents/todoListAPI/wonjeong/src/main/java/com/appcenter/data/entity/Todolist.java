package com.appcenter.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Todolist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;

    private String title;

    private String contents;

    // 다대일 어노테이션
    // Fetch에는 두가지 타입 존재
    // EAGER는 엔티티 조회시 연관된 엔티티를 즉시 한번에 조회
    // LAZY는 실제 사용 시점에 조회
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_NUMBER")
    private Member member;

}
