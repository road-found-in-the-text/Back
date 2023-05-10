package com.example.Back.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "data"})
public class ResponseBody<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    private final String message; //메시지 전달

    private final int code; //내부 코드

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;


    public ResponseBody(T data) {
        this.isSuccess = true;
        this.message = "요청 성공";
        this.code = 200;
        this.data = data;
    }

}
