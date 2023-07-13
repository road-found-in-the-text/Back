package com.example.Back.repository;

import com.example.Back.domain.AssessmentPracticeInfo;
import com.example.Back.repository.Impl.AssessmentPracticeInfoRepositoryImpl;
import com.example.Back.repository.custom.AssessmentPracticeInfoRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentPracticeInfoRepository extends JpaRepository<AssessmentPracticeInfo,Long>, AssessmentPracticeInfoRepositoryCustom {

}
