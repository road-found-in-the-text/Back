package com.example.Back.repository.custom;

import com.example.Back.domain.AssessmentQuestion;

import java.util.List;

public interface AssessmentQuestionRepositoryCustom {
    List<AssessmentQuestion> findAllOrderBySequenceAndSubSequence();
}
