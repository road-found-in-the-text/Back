package com.example.Back.repository.custom;

import com.example.Back.domain.AssessmentPracticeInfo;

public interface AssessmentPracticeInfoRepositoryCustom {

    AssessmentPracticeInfo findByScriptIdAndScoreCount(Long script_id, Integer score_count);
}
