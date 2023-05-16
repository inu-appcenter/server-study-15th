package com.appcenter.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {

    private String name;
    private String password;
    private String email;

    public MemberDTO(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
