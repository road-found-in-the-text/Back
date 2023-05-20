package com.example.Back.repository.custom;

import com.example.Back.domain.AssessmentMemo;

import java.util.List;

public interface AssessmentMemoRepositoryCustom {

    List<AssessmentMemo> findMemoByScriptIdOrderByScoreCount(Long script_id);
}
