package com.appcenter.data.entity;

import com.appcenter.data.dto.request.MemberRequestDTO;
import lombok.AllArgsConstructor;
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
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
@Builder
public class Member implements UserDetails {
    // Id 어노테이션
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String uid;

    @Column(nullable = false)
    private String password;

    @Column
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Builder
    public Member(Long id, String name, String password, String email, String uid, List<String> roles) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.uid = uid;
        this.roles = roles;
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


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
