package com.example.Back.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class AssessmentMemoRes {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getMemo{
        private List<MemoInfo> memos;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemoInfo{
        private Integer score_count;
        private String memo;
    }
}
