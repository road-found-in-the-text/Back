package com.example.Back.repository;

import com.example.Back.domain.AssessmentQuestionScore;
import com.example.Back.repository.custom.AssessmentQuestionScoreRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessmentQuestionScoreRepository extends JpaRepository<AssessmentQuestionScore,Long>, AssessmentQuestionScoreRepositoryCustom {
}
