package com.example.Back.repository.Impl;

import com.example.Back.domain.AssessmentQuestionScore;
import com.example.Back.domain.QAssessmentQuestionScore;
import com.example.Back.repository.custom.AssessmentQuestionScoreRepositoryCustom;
//import com.example.Back.
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import static com.example.Back.domain.QAssessmentQuestionScore.assessmentQuestionScore;


public class AssessmentQuestionScoreRepositoryImpl implements AssessmentQuestionScoreRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public AssessmentQuestionScoreRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<AssessmentQuestionScore> findScoresByScoreCount(Integer score_count) {
        return jpaQueryFactory.selectFrom(assessmentQuestionScore)
                .where(assessmentQuestionScore.score_count.eq(score_count))
                .orderBy(assessmentQuestionScore.assessmentQuestion.sequence.asc())
                .fetch();
    }
}
