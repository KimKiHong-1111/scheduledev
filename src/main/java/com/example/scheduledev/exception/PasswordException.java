package com.example.scheduledev.exception;

public class PasswordException extends CustomException {
    public PasswordException(ErrorCode errorCode) {
      super(errorCode);
    }
}
