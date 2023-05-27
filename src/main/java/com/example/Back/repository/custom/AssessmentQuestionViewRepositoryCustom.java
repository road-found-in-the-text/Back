package com.example.Back.repository.custom;

import com.example.Back.domain.AssessmentQuestionScore;
import com.example.Back.domain.AssessmentQuestionView;
import com.example.Back.dto.request.AssessmentQuestionViewReq;

import java.util.List;

public interface AssessmentQuestionViewRepositoryCustom {

    List<AssessmentQuestionViewReq.QuestionGroupBySequenceDto>findSequence_0ByScoreCountGroupByScoreAndCount(Integer score_count);

    List<AssessmentQuestionView> findViewByScoreCount(Integer score_count);
}
