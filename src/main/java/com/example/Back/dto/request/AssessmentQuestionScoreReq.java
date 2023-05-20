package com.example.Back.dto.request;

import com.example.Back.dto.response.AssessmentQuestionScoreRes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class AssessmentQuestionScoreReq {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class createQuestionScore{
        private List<QuestionScore> questionScores;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QuestionScore {
        private Integer question_sequence;
        private Integer score;
    }
}
