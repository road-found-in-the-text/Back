package com.example.Back.service;

import com.example.Back.domain.AssessmentQuestion;
import com.example.Back.repository.AssessmentQuestionRepository;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class AssessmentQuestionServiceTest {

    @Autowired
    private AssessmentQuestionRepository assessmentQuestionRepository;

    @Test
    public void 항목_저장_test() throws Exception{
        //given
        List<AssessmentQuestion> assessmentQuestionList = new ArrayList<>();
        AssessmentQuestion assessmentQuestion1 = new AssessmentQuestion(1L,1,0,"내용1");
        AssessmentQuestion assessmentQuestion2 = new AssessmentQuestion(2L,1,1,"내용2");
        AssessmentQuestion assessmentQuestion3 = new AssessmentQuestion(3L,1,2,"내용3");
        AssessmentQuestion assessmentQuestion4 = new AssessmentQuestion(4L,1,3,"내용4");
        assessmentQuestionList.add(assessmentQuestion1);
        assessmentQuestionList.add(assessmentQuestion2);
        assessmentQuestionList.add(assessmentQuestion3);
        assessmentQuestionList.add(assessmentQuestion4);

        //when
        assessmentQuestionRepository.saveAllAndFlush(assessmentQuestionList);

        //then
        System.out.println("insert문이 몇개 나가는지 확인");
    }
}
