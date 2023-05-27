package com.example.Back.domain;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "assessment_question_view")
@Where(clause = "deleted = false")
public class AssessmentQuestionView extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="question_score_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "script_id")
    private Script script;

    private Integer sequence;

    private String question_category;

    private Double score;

    private Integer score_count;

    private boolean deleted;
}
