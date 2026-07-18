package com.nap.tomopet.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {

    private String username;
    private String password;
    private String email;
    private String nickname;

}
