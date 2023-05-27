package com.example.Back.repository.Impl;

import com.example.Back.domain.AssessmentQuestionView;
import com.example.Back.dto.request.AssessmentQuestionViewReq;
import com.example.Back.repository.custom.AssessmentQuestionViewRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import static com.example.Back.domain.QAssessmentQuestionScore.assessmentQuestionScore;
import static com.example.Back.domain.QAssessmentQuestionView.assessmentQuestionView;

public class AssessmentQuestionViewRepositoryImpl implements AssessmentQuestionViewRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public AssessmentQuestionViewRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public List<AssessmentQuestionViewReq.QuestionGroupBySequenceDto> findSequence_0ByScoreCountGroupByScoreAndCount(
            Integer score_count
    ) {

        return jpaQueryFactory.select(
                        Projections.constructor(
                                AssessmentQuestionViewReq.QuestionGroupBySequenceDto.class,
                                assessmentQuestionScore.sequence,
                                assessmentQuestionScore.score.sum(),
                                assessmentQuestionScore.count()
                        )
                )
                .from(assessmentQuestionScore)
                .where(assessmentQuestionScore.score_count.eq(score_count))
                .groupBy(assessmentQuestionScore.sequence)
                .fetch();
    }

    @Override
    public List<AssessmentQuestionView> findViewByScoreCount(Integer score_count) {

        return jpaQueryFactory.selectFrom(assessmentQuestionView)
                .where(assessmentQuestionView.score_count.eq(score_count))
                .orderBy(assessmentQuestionView.sequence.asc())
                .fetch();
    }
}
