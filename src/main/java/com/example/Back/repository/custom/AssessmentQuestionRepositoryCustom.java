package com.example.Back.repository.custom;

import com.example.Back.domain.AssessmentQuestion;

import java.util.List;

public interface AssessmentQuestionRepositoryCustom {
    List<AssessmentQuestion> findByDeletedFalse();
    List<AssessmentQuestion> findAllOrderBySequenceAndSubSequence();

    List<AssessmentQuestion> findAllSub_Sequence0();

    List<Long> findBySequenceAndSubSequence(Integer sequence, Integer sub_sequence);

    List<Long> findAllId();
}
