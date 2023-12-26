package com.appcenter.data.dto.common;

import lombok.Getter;

@Getter
public enum CommonResponse {

    SUCCESS(0, "Success"),
    FAIL(-1, "Fail");

    final int code;
    final String msg;

    CommonResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
