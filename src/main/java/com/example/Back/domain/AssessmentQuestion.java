package com.example.Back.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "assessment_question")
public class AssessmentQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="question_id")
    private Long id;

    private Integer sequence;
    private Integer sub_sequence;
    private String question_name;


}
