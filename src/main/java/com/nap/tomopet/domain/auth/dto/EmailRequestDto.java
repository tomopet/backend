package com.nap.tomopet.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class EmailRequestDto {

    @Getter
    @NoArgsConstructor
    public static class Send {
        private String email;
    }

    @Getter
    @NoArgsConstructor
    public static class Verify {
        private String email;
        private String code;
    }

}
