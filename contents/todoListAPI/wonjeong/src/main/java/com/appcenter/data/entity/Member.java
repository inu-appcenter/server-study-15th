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

@Getter
@Entity // 이 클래스가 엔티티 클래스임을 명시
@NoArgsConstructor
@AllArgsConstructor
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

    public void setMember(MemberRequestDTO memberRequestDTO) {
        this.name = memberRequestDTO.getName();
        this.password = memberRequestDTO.getPassword();
        this.email = memberRequestDTO.getEmail();
    }

    public void setMember(Long id, MemberRequestDTO memberRequestDTO) {
        this.id = id;
        this.name = memberRequestDTO.getName();
        this.password = memberRequestDTO.getPassword();
        this.email = memberRequestDTO.getEmail();
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
