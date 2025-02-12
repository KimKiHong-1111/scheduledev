package com.example.scheduledev.exception;


import com.example.scheduledev.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<ErrorResponseDto> handlePasswordException(PasswordException exception) {
        log.error("HttpStatus.UNAUTHORIZED 예외 발생");
        ErrorCode errorCode = exception.getErrorCode();
        return ErrorResponseDto.errorResponse(errorCode.getErrorcode(),exception.getMessage());
    }

}
