package com.example.Back.repository.Impl;

import com.example.Back.domain.AssessmentQuestion;
import com.example.Back.repository.custom.AssessmentQuestionRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import static com.example.Back.domain.QAssessmentQuestion.assessmentQuestion;

public class AssessmentQuestionRepositoryImpl implements AssessmentQuestionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public AssessmentQuestionRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<AssessmentQuestion> findAllOrderBySequenceAndSubSequence() {
        return jpaQueryFactory
                .selectFrom(assessmentQuestion)
                .orderBy(assessmentQuestion.sequence.asc(),assessmentQuestion.sub_sequence.asc())
                .fetch();
    }

    @Override
    public List<AssessmentQuestion> findAllSub_Sequence0() {
        return jpaQueryFactory.selectFrom(assessmentQuestion)
                .where(assessmentQuestion.sub_sequence.eq(0))
                .orderBy(assessmentQuestion.sequence.asc())
                .fetch();
    }
}
