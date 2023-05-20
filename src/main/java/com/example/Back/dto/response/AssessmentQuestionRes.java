package com.example.Back.dto.response;

import com.example.Back.domain.Script;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage;

import java.util.List;

public class AssessmentQuestionRes {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getQuestion{
        private List<QuestionInfo> questions;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QuestionInfo {
        private Integer sequence;
        private Integer sub_sequence;
        private String question_name;
    }
}
