package com.example.Back.dto.request;

import com.example.Back.dto.response.AssessmentQuestionRes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AssessmentQuestionReq {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class createQuestion {
        private List <QuestionInfo> questionInfos;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class updateQuestion {
        private List <QuestionInfo> questionInfos;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QuestionInfo {

        @NotNull(message = "평가 항목의 순서를 적어주세요")
        private Integer sequence;

        @NotNull(message = "평가 항목의 내부 순서를 적어주세요.")
        private Integer sub_sequence;

        @NotNull(message = "평가 내용을 적어주세요")
        private String question_name;
    }
}
