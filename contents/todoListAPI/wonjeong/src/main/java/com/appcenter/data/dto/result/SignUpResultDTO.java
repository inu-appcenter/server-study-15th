package com.appcenter.data.dto.result;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class SignUpResultDTO {
    private boolean success;
    private int code;
    private String msg;

    public void updateSignUpResultDTO (boolean success, int code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }
}
