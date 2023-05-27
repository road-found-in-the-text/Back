package com.example.Back.dto.request;

import com.example.Back.domain.AssessmentQuestion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AssessmentQuestionViewReq {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QuestionGroupBySequenceDto{
        private Integer sequence;
        private Integer score_sum;
        private Long scoreCount;
    }
}
