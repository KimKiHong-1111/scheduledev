package com.example.scheduledev.dto;

import com.example.scheduledev.entity.Member;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    //id,이메일,이름,
    private final Long id;
    private final String email;
    private final String username;

    public LoginResponseDto(Long id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }


    public static LoginResponseDto toDto(Member member) {
        return new LoginResponseDto(member.getId(),member.getUsername(),member.getEmail());
    }
}
