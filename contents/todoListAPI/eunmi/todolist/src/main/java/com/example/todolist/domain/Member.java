package com.example.todolist.domain;

import com.example.todolist.dto.MemberReqDto;
import com.example.todolist.dto.MemberPageRespDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Todo> Todos = new ArrayList<>();

    @Builder
    public Member(String name, String nickName, String email) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
    }


    /**
     * 생성 메서드
     */
    public Member toMember(MemberReqDto memberReqDto) {
        return Member.builder()
                .name(memberReqDto.getName())
                .nickName(memberReqDto.getNickName())
                .email(memberReqDto.getEmail())
                .build();
    }

    public MemberPageRespDto toMemberPageRespDto(Member member) {
        return MemberPageRespDto.builder()
                .name(member.getName())
                .nickName(member.getNickName())
                .email(member.getEmail())
                .build();
    }

    public MemberReqDto toMemberReqDto(Member member) {
        return MemberReqDto.builder()
                .name(member.getName())
                .nickName(member.getNickName())
                .email(member.getEmail())
                .build();
    }

}
