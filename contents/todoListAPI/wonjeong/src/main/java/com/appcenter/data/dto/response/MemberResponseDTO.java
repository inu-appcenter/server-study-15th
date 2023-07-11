package com.appcenter.data.dto.response;

import com.appcenter.data.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponseDTO {
    private Long id;
    private String name;
    private String password;
    private String email;

    public void setMemberResponse(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.password = member.getPassword();
        this.email = member.getEmail();
    }



}
