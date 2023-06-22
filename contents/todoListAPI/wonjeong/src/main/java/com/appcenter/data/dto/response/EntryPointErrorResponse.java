package com.appcenter.data.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@Builder
public class EntryPointErrorResponse {
    private String msg;

    @Builder
    public EntryPointErrorResponse(String msg) {
       this.msg = msg;
    }

    public EntryPointErrorResponse updateEntryPointErrorResponse(String msg) {
        return EntryPointErrorResponse.builder()
                .msg(msg)
                .build();
    }

}
