package com.appcenter.data.dto.response;

import com.appcenter.data.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponseDTO {
    private Long id;
    private String name;
    private String password;
    private String email;

    @Builder
    public MemberResponseDTO(Long id, String name, String password, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public MemberResponseDTO updateMemberResponse(Member member) {
        return MemberResponseDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .password(member.getPassword())
                .email(member.getEmail())
                .build();
    }

}
