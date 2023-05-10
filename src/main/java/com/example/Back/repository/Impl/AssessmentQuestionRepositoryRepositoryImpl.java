package com.example.Back.repository.Impl;

import com.example.Back.domain.AssessmentQuestion;
import com.example.Back.repository.custom.AssessmentQuestionRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import static com.example.Back.domain.QAssessmentQuestion.assessmentQuestion;

public class AssessmentQuestionRepositoryRepositoryImpl implements AssessmentQuestionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public AssessmentQuestionRepositoryRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<AssessmentQuestion> findAllOrderBySequenceAndSubSequence() {
        return jpaQueryFactory
                .selectFrom(assessmentQuestion)
                .orderBy(assessmentQuestion.sequence.asc(),assessmentQuestion.sub_sequence.asc())
                .fetch();
    }
}
