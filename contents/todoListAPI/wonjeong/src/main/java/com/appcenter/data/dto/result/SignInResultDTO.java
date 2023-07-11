package com.appcenter.data.dto.result;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class SignInResultDTO extends SignUpResultDTO {

    private String token;

    @Builder
    public SignInResultDTO(boolean success, int code, String msg, String token) {
        super(success, code, msg);
        this.token = token;
    }

}
