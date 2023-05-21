package com.example.Back.exception;

import lombok.Getter;

@Getter
public enum ResponseTemplateStatus {
    SUCCESS(true, "요청 성공", 1000),
    FAIL(false, "요청 실패", 1004),

    //4000: 유저 부분 오류
    USER_NOT_FOUND(false, "유저를 찾을 수 없습니다.", 4005);

    private final boolean isSuccess;
    private final String message;
    private final int code;

    private ResponseTemplateStatus(boolean isSuccess, String message, int code){
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
