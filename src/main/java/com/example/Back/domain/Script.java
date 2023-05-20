package com.example.Back.domain;

import lombok.*;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
// @SQLDelete(sql = "UPDATE umc3.script SET deleted = true WHERE script_id = ?")   // JPA Soft Delete
@Where(clause = "deleted = false")
// @JsonIdentityReference(alwaysAsId = true)
// @JsonIgnoreProperties({"hibernateLazyInitializer"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "script")
public class Script extends BaseEntity {
    // AUTO
    @Id                                              // Primary Key와 연결
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 식별자 값을 자동 생성
    @Column(name="script_id")
    private Long scriptId;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "member_id")
    // @JsonBackReference
    // private Member memberId;

    @Column
    private String title;

    @Column
    private int cnt;

    // 몇 번 평가를 수행했는지 측정하기 위한 count
    @Column
    @ColumnDefault("0")
    private Integer score_count;

    @Column
    private boolean deleted;

    @Builder.Default
    @OneToMany(mappedBy = "script", cascade = CascadeType.PERSIST )
    private List<Paragraph> paragraphs = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "script", cascade = CascadeType.PERSIST )
    private List<AssessmentQuestionScore> assessmentQuestionScoreList = new ArrayList<>();
}
