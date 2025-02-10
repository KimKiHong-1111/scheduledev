package com.example.scheduledev.dto;

import lombok.Getter;

@Getter
public class MemberResponseDto {
    //특정 회원 조회 API 구현
    private final Long id;
    private final String username;
    private final String email;

    public MemberResponseDto(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email= email;
    }

}
