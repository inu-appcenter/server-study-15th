package com.appcenter.data.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Builder
@Setter
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
