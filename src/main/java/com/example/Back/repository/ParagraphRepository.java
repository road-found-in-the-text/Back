package com.example.Back.repository;

import com.example.Back.domain.Paragraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParagraphRepository extends JpaRepository<Paragraph, Long>, CrudRepository<Paragraph, Long> {
    Optional<Paragraph> findById(Long id);
}
