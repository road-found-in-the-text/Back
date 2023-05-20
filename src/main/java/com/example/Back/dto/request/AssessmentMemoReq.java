package com.example.Back.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AssessmentMemoReq {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class createMemo{
        private String memo;
    }
}
