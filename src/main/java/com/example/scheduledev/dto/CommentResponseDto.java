package com.example.scheduledev.dto;

import com.example.scheduledev.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long id;
    private Long scheduleId;
    private String contents;
    private Long memberId;
    private String username;
    private LocalDateTime createdAt;

    public CommentResponseDto(Long id, Long scheduleId, String contents, Long memberId, String username, LocalDateTime createdAt) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.contents = contents;
        this.memberId = memberId;
        this.username = username;
        this.createdAt = createdAt;
    }


    public static CommentResponseDto toDto(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getSchedule().getId(),
                comment.getContent(),
                comment.getMember().getId(),
                comment.getMember().getUsername(),
                comment.getCreatedAt());
    }
}
