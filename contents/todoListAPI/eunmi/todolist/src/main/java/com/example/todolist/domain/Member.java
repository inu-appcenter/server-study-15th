package com.example.todolist.domain;

import com.example.todolist.dto.MemberPageRespDto;
import com.example.todolist.dto.MemberRespDto;
import com.example.todolist.dto.SignupReqDto;
import com.example.todolist.dto.UpdateMemberReqDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String userName;   // 유저가 생성할 고유 id(uid)

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Todo> Todos = new ArrayList<>();


    @Builder
    public Member(Long id, String name, String userName, String password, String email, Role role) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
    }


    /**
     * 생성 메서드
     */
    public Member toMember(SignupReqDto signupReqDto) {
        return Member.builder()
                .name(signupReqDto.getName())
                .userName(signupReqDto.getUserName())
                .password(signupReqDto.getPassword())
                .email(signupReqDto.getEmail())
                .role(Role.valueOf(signupReqDto.getRole()))
                .build();
    }

    public void updateMember(UpdateMemberReqDto updateMemberReqDto) {
        this.email = updateMemberReqDto.getEmail();
    }

    public MemberPageRespDto toMemberPageRespDto(Member member) {
        return MemberPageRespDto.builder()
                .name(member.getName())
                .userName(member.getUsername())
                .email(member.getEmail())
                .build();
    }

    public MemberRespDto toMemberRespDto(Member member) {
        return MemberRespDto.builder()
                .userName(member.userName)
                .name(member.getName())
                .email(member.getEmail())
                .role(String.valueOf(member.getRole()))
                .build();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {    // 계정이 가지고 있는 권한 목록 리턴
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.role.getRole()));
        return authorities;
    }

    @Override
    public String getUsername() {  // 계정의 이름을 리턴, 일반적으로 id
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {  // 계정이 만료되었는지 리턴, true는 만료 되지 않음.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {  // 계정이 잠겨있는지 여부
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {   // 비밀번호가 만료되었는지 여부
        return true;
    }

    @Override
    public boolean isEnabled() {    // 계정이 활성회 되어있는지 여부
        return true;
    }
}