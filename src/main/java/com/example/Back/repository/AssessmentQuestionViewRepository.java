package com.example.Back.repository;

import com.example.Back.domain.AssessmentQuestionView;
import com.example.Back.repository.custom.AssessmentQuestionViewRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentQuestionViewRepository extends JpaRepository<AssessmentQuestionView,Long>, AssessmentQuestionViewRepositoryCustom {
}
