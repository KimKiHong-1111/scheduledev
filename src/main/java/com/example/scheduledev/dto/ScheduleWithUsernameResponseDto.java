package com.example.scheduledev.dto;

import lombok.Getter;

@Getter
public class ScheduleWithUsernameResponseDto {
    //제목,작성자,내용
    private final String title;
    private final String username;
    private final String contents;



    public ScheduleWithUsernameResponseDto(String title, String username, String contents) {
        this.title = title;
        this.contents = contents;
        this.username = username;
    }

}
