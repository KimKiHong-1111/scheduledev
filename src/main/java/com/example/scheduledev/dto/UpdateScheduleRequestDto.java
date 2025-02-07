package com.example.scheduledev.dto;

import lombok.Getter;

@Getter
public class UpdateScheduleRequestDto {
    private String email;
    private String password;
    private String title;
    private String contents;
}
