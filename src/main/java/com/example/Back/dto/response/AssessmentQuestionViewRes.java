package com.example.Back.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class AssessmentQuestionViewRes {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getQuestionScore{
        private Integer score_count;
        private Double score_avg;
        private List<AssessmentQuestionScoreRes.QuestionScore> before_scores;
        private List<AssessmentQuestionScoreRes.QuestionScore> current_scores;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QuestionScore {
        private Integer question_sequence;
        private String question_name;
        private Double score;
    }
}
