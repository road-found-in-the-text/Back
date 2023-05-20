package com.example.Back.repository;

import com.example.Back.domain.AssessmentMemo;
import com.example.Back.repository.Impl.AssessmentMemoRepositoryImpl;
import com.example.Back.repository.custom.AssessmentMemoRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessmentMemoRepository extends JpaRepository<AssessmentMemo,Long>, AssessmentMemoRepositoryCustom {
}
