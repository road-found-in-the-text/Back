package com.example.Back.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    SCRIPT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 script를 찾을 수 없습니다.")

    ;

    private final HttpStatus httpStatus;
    private final String message;
}
