package com.example.Back.repository;

import com.example.Back.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialMemberRepository extends JpaRepository<Member,String> {
    Optional<Member> findBySocialId(String socialId);
}
