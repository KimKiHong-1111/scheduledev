package com.example.scheduledev.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long scheduleId;
    private String contents;
}
