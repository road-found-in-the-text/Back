package com.example.Back.service;

import com.example.Back.domain.AssessmentQuestion;
import com.example.Back.domain.AssessmentQuestionScore;
import com.example.Back.dto.request.AssessmentQuestionReq;
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
        AssessmentQuestion assessmentQuestion1 = new AssessmentQuestion(1,0,"내용1");
        AssessmentQuestion assessmentQuestion2 = new AssessmentQuestion(1,1,"내용2");
        AssessmentQuestion assessmentQuestion3 = new AssessmentQuestion(1,2,"내용3");
        AssessmentQuestion assessmentQuestion4 = new AssessmentQuestion(1,3,"내용4");
        assessmentQuestionList.add(assessmentQuestion1);
        assessmentQuestionList.add(assessmentQuestion2);
        assessmentQuestionList.add(assessmentQuestion3);
        assessmentQuestionList.add(assessmentQuestion4);

        //when
        assessmentQuestionRepository.saveAllAndFlush(assessmentQuestionList);

        //then
        System.out.println("insert문이 몇개 나가는지 확인");
    }

    @Test
    public void 항복_update() throws Exception{
        //given
        List<AssessmentQuestion> assessmentQuestionList = new ArrayList<>();
        AssessmentQuestion assessmentQuestion1 = new AssessmentQuestion(1,0,"내용1");
        AssessmentQuestion assessmentQuestion2 = new AssessmentQuestion(1,1,"내용2");
        AssessmentQuestion assessmentQuestion3 = new AssessmentQuestion(1,2,"내용3");
        AssessmentQuestion assessmentQuestion4 = new AssessmentQuestion(1,3,"내용4");
        assessmentQuestionList.add(assessmentQuestion1);
        assessmentQuestionList.add(assessmentQuestion2);
        assessmentQuestionList.add(assessmentQuestion3);
        assessmentQuestionList.add(assessmentQuestion4);

        assessmentQuestionRepository.saveAllAndFlush(assessmentQuestionList);
        //when

        // requestbody로 기존의 데이터와 바뀐 데이터 모두를 가지고 온다.
        List<AssessmentQuestionReq.QuestionInfo> questionInfos = new ArrayList<>();
        AssessmentQuestionReq.updateQuestion updateQuestion = new AssessmentQuestionReq.updateQuestion(questionInfos);

        AssessmentQuestionReq.QuestionInfo questionInfo1 = new AssessmentQuestionReq.QuestionInfo(1,0,"내용1");
        AssessmentQuestionReq.QuestionInfo questionInfo2 = new AssessmentQuestionReq.QuestionInfo(1,1,"내용1_1");
        AssessmentQuestionReq.QuestionInfo questionInfo3 = new AssessmentQuestionReq.QuestionInfo(1,2,"내용3");
        AssessmentQuestionReq.QuestionInfo questionInfo4 = new AssessmentQuestionReq.QuestionInfo(1,3,"내용1_2");
        AssessmentQuestionReq.QuestionInfo questionInfo5 = new AssessmentQuestionReq.QuestionInfo(2,0,"내용2");

        questionInfos.add(questionInfo1);
        questionInfos.add(questionInfo2);
        questionInfos.add(questionInfo3);
        questionInfos.add(questionInfo4);
        questionInfos.add(questionInfo5);

//        for(AssessmentQuestionReq.QuestionInfo questionInfo : questionInfos){
//            List<Long> findAssessmentQuestion = assessmentQuestionRepository.findBySequenceAndSubSequence(
//                    questionInfo.getSequence(),questionInfo.getSub_sequence());
//            AssessmentQuestion assessmentQuestion = null;
//            if(findAssessmentQuestion.size() != 0){
//                assessmentQuestion = findAssessmentQuestion.get(0);
//                assessmentQuestion.setQuestion_name(questionInfo.getQuestion_name());
//            }else{
//                assessmentQuestion = com.example.Back.domain.AssessmentQuestion.builder()
//                        .sequence(questionInfo.getSequence())
//                        .sub_sequence(questionInfo.getSub_sequence())
//                        .question_name(questionInfo.getQuestion_name())
//                        .build();
//            }
//
//            assessmentQuestionRepository.save(assessmentQuestion);
//        }


        //then

    }
}
