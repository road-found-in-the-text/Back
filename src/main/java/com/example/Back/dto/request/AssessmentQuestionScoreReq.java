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
        private Integer practice_minute;
        private Integer practice_second;
        private List<QuestionScore> questionScores;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QuestionScore {
        private Long question_id;
        private Integer score;
    }
}
