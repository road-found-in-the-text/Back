package com.example.Back.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class AssessmentQuestionScoreRes {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class createQuestionScore{
        private Integer score_count;
        private List<QuestionScore> questionScores;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QuestionScore {
        private Integer question_sequence;
        private String question_name;
        private Double score;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getQuestionScore{
        private Integer score_count;
        private Double score_avg;
        private List<QuestionScore> before_scores;
        private List<QuestionScore> current_scores;
    }
}
