package com.example.Back.repository.Impl;

import com.example.Back.domain.AssessmentPracticeInfo;
import com.example.Back.repository.custom.AssessmentPracticeInfoRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.example.Back.domain.QAssessmentPracticeInfo.assessmentPracticeInfo;

public class AssessmentPracticeInfoRepositoryImpl implements AssessmentPracticeInfoRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public AssessmentPracticeInfoRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public AssessmentPracticeInfo findBeforeAssessmentPracticeInfo(Long script_id, Integer score_count) {
        return jpaQueryFactory.selectFrom(assessmentPracticeInfo)
                .where(assessmentPracticeInfo.script.scriptId.eq(script_id).and(assessmentPracticeInfo.score_count.eq(score_count)))
                .fetchOne();
    }
}
