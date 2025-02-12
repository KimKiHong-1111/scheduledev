package com.example.scheduledev.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;

    public CustomException(ErrorCode errorCode) {
        //부모한테 가는것..!
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
        this.message = errorCode.getErrorMessage();
    }

    //예외처리 할만한거
    //유저 중복,
    //필터 거칠때 500에러뜨는거 400으로 바꾸고....?...??>>?>?>?>?>?>
    //500 제잘못 400은 고객잘못.
}
