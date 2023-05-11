package com.example.Back.domain;

import jakarta.persistence.*;
import lombok.Builder;

public class Member{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="memberId")
    private Long id;
    @Column
    private String socialId;
    @Column(length=30)
    private String nickName;
    @Enumerated(EnumType.STRING)
    private Tier tier ;

    @Enumerated(EnumType.STRING)
    private LoginType loginType; //일반 로그인 또는 소셜로그인

    @Column(nullable = true)
    private String introduction; //한 줄 소개
    @Column
    private int memberStatus;



//    @OneToMany(mappedBy = "memberId", cascade = CascadeType.ALL)
//    private List<Script> scripts ;

//    @OneToMany(mappedBy = "memberId", cascade = CascadeType.ALL)
//    private List<Paragraph> paragraphs ;

//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
//    private List<Interview> interviews = new ArrayList<>();

//    @OneToMany(mappedBy = "memberId", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<RecordScript> record_script;

//    @OneToMany(mappedBy = "memberId", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<RecordInterview> record_interview;

}