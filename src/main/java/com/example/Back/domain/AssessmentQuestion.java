package com.example.Back.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "assessment_question")
@DynamicUpdate
public class AssessmentQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="question_id")
    private Long id;

    private Integer sequence;
    private Integer sub_sequence;
    private String question_name;

    @ColumnDefault("false")
    private boolean deleted;


    @OneToMany(mappedBy = "assessmentQuestion",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<AssessmentQuestionScore> assessmentQuestionScoreList = new ArrayList<>();

    public AssessmentQuestion(Integer sequence, Integer sub_sequence, String question_name){
        this.question_name = question_name;
        this.sequence = sequence;
        this.sub_sequence = sub_sequence;
    }

}
