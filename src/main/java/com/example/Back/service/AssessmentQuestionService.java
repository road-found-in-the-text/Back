package com.example.Back.service;

import com.example.Back.domain.AssessmentQuestion;
import com.example.Back.dto.request.AssessmentQuestionReq;
import com.example.Back.dto.response.AssessmentQuestionRes;
import com.example.Back.dto.response.ResponseBody;
import com.example.Back.repository.AssessmentQuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AssessmentQuestionService {

    private final AssessmentQuestionRepository assessmentQuestionRepository;

    public ResponseBody<AssessmentQuestionRes.getQuestion> getQuestion() {

        List<AssessmentQuestion> allQuestion = assessmentQuestionRepository.findAllOrderBySequenceAndSubSequence();

        return new ResponseBody(new AssessmentQuestionRes.getQuestion(allQuestion.stream().map(
                assessmentQuestion ->
                new AssessmentQuestionRes.QuestionInfo(assessmentQuestion.getId(),assessmentQuestion.getSequence(),assessmentQuestion.getSub_sequence(),
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
        List<AssessmentQuestion> allDbAssessmentQuestion = assessmentQuestionRepository.findAll();

        for(AssessmentQuestion assessmentQuestion:allDbAssessmentQuestion){
            assessmentQuestion.setDeleted(true);
        }

        assessmentQuestionRepository.saveAll(allDbAssessmentQuestion);

        return new ResponseBody("삭제 완료되었습니다.");
    }

    @Transactional
    public ResponseBody<AssessmentQuestionRes.getQuestion> updateQuestion(AssessmentQuestionReq.updateQuestion request) {
        List<AssessmentQuestion> allDbAssessmentQuestion = assessmentQuestionRepository.findByDeletedFalse();
        log.info("여기까지가 findAll 끝이다.");

        Map<Long, AssessmentQuestion> map = new HashMap<>();
        for(AssessmentQuestion assessmentQuestion : allDbAssessmentQuestion){
            map.put(assessmentQuestion.getId(),assessmentQuestion);
        }

        List<AssessmentQuestion> allQuestion = new ArrayList<>();

        for(AssessmentQuestionReq.QuestionInfo questionInfo : request.getQuestionInfos()){
            List<Long> findQuestionId = assessmentQuestionRepository.findBySequenceAndSubSequence(questionInfo.getSequence(), questionInfo.getSub_sequence());
            AssessmentQuestion assessmentQuestion = null;

            if(findQuestionId.size() != 0){
                assessmentQuestion = map.get(findQuestionId.get(0));
                map.remove(assessmentQuestion.getId());
                log.info("update 시작");
                assessmentQuestion.setQuestion_name(questionInfo.getQuestion_name());
                log.info("와우 update 끝");
            }else{
                assessmentQuestion = AssessmentQuestion.builder()
                        .sequence(questionInfo.getSequence())
                        .sub_sequence(questionInfo.getSub_sequence())
                        .question_name(questionInfo.getQuestion_name())
                        .build();
            }

            allQuestion.add(assessmentQuestion);
            log.info("여기서부터 save(assessmentQuestion) 시작");
            assessmentQuestionRepository.save(assessmentQuestion);
            log.info("끝");
        }
        List<AssessmentQuestion> deleteAssessmentQuestions = map.values().stream().toList();
        for (AssessmentQuestion deleteAssessmetnQuestion : deleteAssessmentQuestions){
            log.info("delete 시작");
            deleteAssessmetnQuestion.setDeleted(true);
            assessmentQuestionRepository.delete(deleteAssessmetnQuestion);
            log.info("와우 delete 끝");
        }

        return new ResponseBody(new AssessmentQuestionRes.getQuestion(allQuestion.stream().map(
                assessmentQuestion ->
                        new AssessmentQuestionRes.QuestionInfo(assessmentQuestion.getId(),assessmentQuestion.getSequence(),assessmentQuestion.getSub_sequence(),
                                assessmentQuestion.getQuestion_name())
        ).collect(Collectors.toList())));

    }
}
