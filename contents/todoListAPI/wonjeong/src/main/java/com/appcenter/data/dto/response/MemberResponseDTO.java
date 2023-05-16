package com.appcenter.data.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponseDTO {

    private Long number;
    private String name;
    private String password;
    private String email;

    public MemberResponseDTO() {

    }

    public MemberResponseDTO(Long number, String name, String password, String email) {
        this.number = number;
        this.name = name;
        this.password = password;
        this.email = email;
    }

}
