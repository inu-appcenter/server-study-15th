package com.appcenter.data.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberResponseDTO {

    private Long id;
    private String name;
    private String password;
    private String email;

}
