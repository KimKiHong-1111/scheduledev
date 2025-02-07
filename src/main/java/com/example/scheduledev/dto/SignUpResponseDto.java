package com.example.scheduledev.dto;

import lombok.Getter;

@Getter
public class SignUpResponseDto {
    //식별자
    private final Long id;

    private final String username;

    private final String password;

    public SignUpResponseDto(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
