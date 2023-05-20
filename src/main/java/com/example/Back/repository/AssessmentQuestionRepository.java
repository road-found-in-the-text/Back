package com.example.Back.repository;

import com.example.Back.domain.AssessmentQuestion;
import com.example.Back.repository.custom.AssessmentQuestionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessmentQuestionRepository extends JpaRepository<AssessmentQuestion, Long> , AssessmentQuestionRepositoryCustom {

}
