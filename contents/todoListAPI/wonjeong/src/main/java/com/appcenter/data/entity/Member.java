package com.appcenter.data.entity;

import com.appcenter.data.dto.request.MemberRequestDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity // 이 클래스가 엔티티 클래스임을 명시
@NoArgsConstructor
public class Member {
    // Id 어노테이션
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column
    private String email;

    @Builder
    public Member(Long id, String name, String password, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public Member createMember(MemberRequestDTO memberRequestDTO) {
        return Member.builder()
                .name(memberRequestDTO.getName())
                .password(memberRequestDTO.getPassword())
                .email(memberRequestDTO.getEmail())
                .build();
    }

    // 업데이트 메서드
    public Member updateMember(Long id, MemberRequestDTO memberRequestDTO) {
        return Member.builder()
                .id(id)
                .name(memberRequestDTO.getName())
                .password(memberRequestDTO.getPassword())
                .email(memberRequestDTO.getEmail())
                .build();
    }



}
