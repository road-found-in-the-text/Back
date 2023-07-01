package com.example.Back.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "assessment_practive_info")
@DynamicUpdate
public class AssessmentPracticeInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="question_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "script_id")
    private Script script;

    private Integer score_count;

    @Column
    @ColumnDefault("0")
    private Integer current_practice_hour;
    @Column
    @ColumnDefault("0")
    private Integer current_practice_minute;
    @Column
    @ColumnDefault("0")
    private Integer current_practice_second;

    @Column
    @ColumnDefault("0")
    private Integer total_practice_hour;
    @Column
    @ColumnDefault("0")
    private Integer total_practice_minute;
    @Column
    @ColumnDefault("0")
    private Integer total_practice_second;

}
