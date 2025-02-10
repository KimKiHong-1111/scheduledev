package com.example.scheduledev.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateScheduleRequestDto {
    @NotBlank
    @Size(max = 10, message = "제목은 10글자 이내로 입력해주세요.")
    private final String title;

    @NotBlank(message = "내용은 필수 입력값입니다.")
    private final String contents;

    @NotBlank(message = "유저명은 필수 입력값입니다.")
    @Size(max = 4, message = "유저명은 4글자 이내로 입력해주세요.")
    private final String username;
    //비밀번호가 없나?
    public CreateScheduleRequestDto(String title, String contents, String username) {
        this.title = title;
        this.contents = contents;
        this.username = username;
    }
}
