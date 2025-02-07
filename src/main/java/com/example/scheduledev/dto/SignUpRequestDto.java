package com.example.scheduledev.dto;

import lombok.Getter;

@Getter
public class SignUpRequestDto {

    //제일 먼저 만들기 사용자명, 이메일, 비밀번호
    private final String username;
    private final String email;
    private final String password;

    public SignUpRequestDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
