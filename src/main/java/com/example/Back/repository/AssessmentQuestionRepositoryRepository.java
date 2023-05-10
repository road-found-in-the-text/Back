package com.example.Back.repository;

import com.example.Back.domain.AssessmentQuestion;
import com.example.Back.repository.custom.AssessmentQuestionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentQuestionRepositoryRepository extends JpaRepository<AssessmentQuestion, Long> , AssessmentQuestionRepositoryCustom {

}
