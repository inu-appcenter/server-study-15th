package com.appcenter.data.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeMemberDTO {
    private Long number;
    private String name;
    private String password;
    private String email;

    public ChangeMemberDTO() {

    }

    public ChangeMemberDTO(Long number, String name, String password, String email) {
        this.number = number;
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
