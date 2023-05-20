package com.example.Back.service;

import com.example.Back.domain.AssessmentQuestion;
import com.example.Back.dto.request.AssessmentQuestionReq;
import com.example.Back.dto.response.AssessmentQuestionRes;
import com.example.Back.dto.response.ResponseBody;
import com.example.Back.repository.AssessmentQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AssessmentQuestionService {

    private final AssessmentQuestionRepository assessmentQuestionRepository;

    public ResponseBody<AssessmentQuestionRes.getQuestion> getQuestion() {

        List<AssessmentQuestion> allQuestion = assessmentQuestionRepository.findAllOrderBySequenceAndSubSequence();

        return new ResponseBody(new AssessmentQuestionRes.getQuestion(allQuestion.stream().map(
                assessmentQuestion ->
                new AssessmentQuestionRes.QuestionInfo(assessmentQuestion.getSequence(),assessmentQuestion.getSub_sequence(),
                        assessmentQuestion.getQuestion_name())
        ).collect(Collectors.toList())));
    }

    @Transactional
    public ResponseBody<String> createQuestion(AssessmentQuestionReq.createQuestion request) {
        List<AssessmentQuestionReq.QuestionInfo> questionInfos = request.getQuestionInfos();
        List<AssessmentQuestion> assessmentQuestionList = new ArrayList<>();

        for(AssessmentQuestionReq.QuestionInfo questionInfo : questionInfos ){
            AssessmentQuestion assessmentQuestion = AssessmentQuestion.builder()
                    .sequence(questionInfo.getSequence())
                    .sub_sequence(questionInfo.getSub_sequence())
                    .question_name(questionInfo.getQuestion_name())
                    .build();
            assessmentQuestionList.add(assessmentQuestion);
        }

        assessmentQuestionRepository.saveAll(assessmentQuestionList);

        return new ResponseBody("db에 정상적으로 저장되었습니다.");
    }

    @Transactional
    public ResponseBody<String> deleteQuestion() {
        assessmentQuestionRepository.deleteAll();
        return new ResponseBody("삭제 완료되었습니다.");
    }
}