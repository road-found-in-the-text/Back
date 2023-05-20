package com.example.Back.repository.Impl;


import com.example.Back.domain.AssessmentMemo;
import com.example.Back.repository.custom.AssessmentMemoRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;
import static com.example.Back.domain.QAssessmentMemo.assessmentMemo;

public class AssessmentMemoRepositoryImpl implements AssessmentMemoRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public AssessmentMemoRepositoryImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<AssessmentMemo> findMemoByScriptIdOrderByScoreCount(Long script_id) {
        return jpaQueryFactory.selectFrom(assessmentMemo)
                .where(assessmentMemo.script.scriptId.eq(script_id))
                .orderBy(assessmentMemo.score_count.asc())
                .fetch();
    }
}
