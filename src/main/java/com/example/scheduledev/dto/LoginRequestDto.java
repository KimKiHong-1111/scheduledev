package com.example.scheduledev.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    //로그인 요청할때는 email, password 만 있으면되겠다.
    private final String email;
    private final String password;

    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
