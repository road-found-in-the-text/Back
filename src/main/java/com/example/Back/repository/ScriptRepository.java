package com.example.Back.repository;

import com.example.Back.domain.Script;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScriptRepository extends JpaRepository<Script, Long>, CrudRepository<Script, Long> {
    Optional<Script> findById(Long id);
    List<Script> findByMemberId(Long memberId);

    List<Script> findAll();

}
