package com.example.Back.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Where(clause = "deleted = false")
@AllArgsConstructor
@NoArgsConstructor
// @JsonIdentityReference(alwaysAsId = true)
@Entity
@Table(name = "paragraph")
public class Paragraph extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="paragraph_id")
    private Long paragraphId;

    @ManyToOne(targetEntity =Script.class, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    @JoinColumn(name="script_id")
    private Script script;

    @Column
    private int idx;

    @Column
    private String title;

    @Column
    private String contents;

    @Column
    private boolean deleted;
}
