package com.example.Back.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@Getter
public class CustomException extends RuntimeException {
    ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.toString());
        Objects.requireNonNull(errorCode, "errorCode must not be null");
        this.errorCode = errorCode;
    }
}
