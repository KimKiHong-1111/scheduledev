package com.example.scheduledev.dto;

import lombok.Getter;

@Getter
public class CreateScheduleRequestDto {
    private final String title;
    private final String contents;
    private final String username;
    //비밀번호가 없나?
    public CreateScheduleRequestDto(String title, String contents, String username) {
        this.title = title;
        this.contents = contents;
        this.username = username;
    }
}
