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

    //이게 없다면 생기는 단점??
    //장점 1. 객체 생성 없이 호출 가능
    //장점 2. 불필요한 객체 생성을 방지 (메모리 절약)
    //장점 3. 유틸리티 성격의 메서드에 적합
    //장점 4. 다른 클래스에서 쉽게 재사용 가능
    //장점 5. 객체 지향적인 접근 유지 (OOP 위배 X)
    //강의에 있었다.
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
