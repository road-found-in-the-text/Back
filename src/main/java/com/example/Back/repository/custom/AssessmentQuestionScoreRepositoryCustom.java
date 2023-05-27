package com.example.Back.repository.custom;

import com.example.Back.domain.AssessmentQuestionScore;

import java.util.List;

public interface AssessmentQuestionScoreRepositoryCustom {

    List<AssessmentQuestionScore> findScoresByScoreCount(Integer score_count);

}
