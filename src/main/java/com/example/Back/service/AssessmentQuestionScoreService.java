package com.example.Back.service;

import com.example.Back.domain.*;
import com.example.Back.dto.request.AssessmentQuestionScoreReq;
import com.example.Back.dto.response.AssessmentQuestionScoreRes;
import com.example.Back.dto.response.ResponseBody;
import com.example.Back.exception.CustomException;
import com.example.Back.exception.ErrorCode;
import com.example.Back.repository.AssessmentPracticeInfoRepository;
import com.example.Back.repository.AssessmentQuestionRepository;
import com.example.Back.repository.AssessmentQuestionScoreRepository;
import com.example.Back.repository.ScriptRepository;
import com.mysema.commons.lang.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AssessmentQuestionScoreService {

    private final ScriptRepository scriptRepository;
    private final AssessmentQuestionViewService assessmentQuestionViewService;
    private final AssessmentQuestionRepository assessmentQuestionRepository;
    private final AssessmentQuestionScoreRepository assessmentQuestionScoreRepository;

    private final AssessmentPracticeInfoRepository assessmentPracticeInfoRepository;

    Script findScript(Long script_id){
        return scriptRepository.findById(script_id).orElseThrow(
                () -> new CustomException(ErrorCode.SCRIPT_NOT_FOUND)
        );
    }

    List<AssessmentQuestion> findAllQuestion_SubSequence0(){
        return assessmentQuestionRepository.findAllSub_Sequence0();
    }

    @Transactional
    public ResponseBody<AssessmentQuestionScoreRes.getQuestionScore> createQuestionScore(
            Long script_id, AssessmentQuestionScoreReq.createQuestionScore request) {

        // 해당 script를 찾는다.
        Script findScript = findScript(script_id);

        // 해당 연습의 걸린 시간과 분을 저장.
        AssessmentPracticeInfo assessmentPracticeInfo = AssessmentPracticeInfo.builder()
                .current_practice_minute(request.getPractice_minute())
                .current_practice_second(request.getPractice_second())
                .score_count(findScript.getScore_count()+1)
                .script(findScript)
                .build();
        Integer total_practice_hour = 0;
        Integer total_practice_minute = 0;
        Integer total_practice_second = 0;

        Integer before_score_count = findScript.getScore_count();

        if(before_score_count == null){
            before_score_count = 0;
        }

        AssessmentPracticeInfo beforeAssessmentInfo = assessmentPracticeInfoRepository.findBeforeAssessmentPracticeInfo(findScript.getScriptId(),before_score_count);

        if(beforeAssessmentInfo == null){
            total_practice_minute = request.getPractice_minute();
            total_practice_second = request.getPractice_second();
        }else{
            total_practice_hour = beforeAssessmentInfo.getTotal_practice_hour();
            total_practice_minute = beforeAssessmentInfo.getTotal_practice_minute() + request.getPractice_minute();
            total_practice_second = beforeAssessmentInfo.getTotal_practice_second() + request.getPractice_second();

            if(total_practice_second >= 60){
                total_practice_minute += total_practice_second /60;
                total_practice_second = total_practice_second % 60;
            }

            if(total_practice_minute >= 60 ){
                total_practice_hour += total_practice_minute / 60;
                total_practice_minute = total_practice_minute % 60;
            }
        }

        assessmentPracticeInfo.setTotal_practice_hour(total_practice_hour);
        assessmentPracticeInfo.setTotal_practice_minute(total_practice_minute);
        assessmentPracticeInfo.setTotal_practice_second(total_practice_second);

        assessmentPracticeInfoRepository.save(assessmentPracticeInfo);

        // 모든 AssessmentQuestion을 가지고 온다.
        List<AssessmentQuestion> allDbAssessmentQuestion = assessmentQuestionRepository.findByDeletedFalse();

        // map<qustion_db_id,AssessmentQuestion>
        // 점수마다 해당하는 qustion_id를 각각 처리하면 질문이 n개면 select를 n번 해야 하기 때문에 한번에 select를 하고
        // map을 사용하여 O(1)로 해당 db_id의 AssessQuestion을 찾도록 했다.
        Map<Long, AssessmentQuestion> assessmentQuestionMap = new HashMap<>();

        for(AssessmentQuestion assessmentQuestion : allDbAssessmentQuestion){
            assessmentQuestionMap.put(assessmentQuestion.getId(),assessmentQuestion);
        }

        // requestbody로 담아온 <db_id,score> list를 가지고 온다.
        List<AssessmentQuestionScoreReq.QuestionScore> questionScores = request.getQuestionScores();

        List<AssessmentQuestionScore> assessmentQuestionScoreList = new ArrayList<>();

        // requestbody에 있는 모든 점수에 대해 for문을 한번 돌려서 일단 AssessmentQuestionScore에 모든 점수를 저장한다.
        // 그리고 보여줄 때는 sub_sequence가 0번인 항목만 계산해서 보여주기 때문에 이 또한 계산을 한번에 한다.
        for(AssessmentQuestionScoreReq.QuestionScore questionScore : questionScores){

            // score 저장.
            AssessmentQuestion assessmentQuestion = assessmentQuestionMap.get(questionScore.getQuestion_id());
            AssessmentQuestionScore assessmentQuestionScore = AssessmentQuestionScore.builder()
                    .score(questionScore.getScore())
                    .score_count(findScript.getScore_count() + 1)
                    .script(findScript)
                    .assessmentQuestion(assessmentQuestion)
                    .sequence(assessmentQuestion.getSequence())
                    .question_name(assessmentQuestion.getQuestion_name())
                    .deleted(false)
                    .build();
            assessmentQuestionScoreList.add(assessmentQuestionScore);
        }

        findScript.setScore_count(findScript.getScore_count()+1);

        scriptRepository.save(findScript);
        assessmentQuestionScoreRepository.saveAllAndFlush(assessmentQuestionScoreList);

        return assessmentQuestionViewService.createQuestionView(findScript);
    }
//
//    public ResponseBody<AssessmentQuestionScoreRes.getQuestionScore> getQuestionScore(Long script_id) {
//
//        Script findScript = findScript(script_id);
//
//        Integer current_score_count = findScript.getScore_count();
//
//        List<AssessmentQuestionScoreRes.QuestionScore> beforeQuestionScores = new ArrayList<>();
//        List<AssessmentQuestionScoreRes.QuestionScore> afterQuestionScores = new ArrayList<>();
//
//        if(current_score_count == 1){
//            beforeQuestionScores = null;
//        }else{
//            List<AssessmentQuestionScore> beforeScoreCount = assessmentQuestionScoreRepository.findScoresByScoreCount(current_score_count - 1);
//            beforeQuestionScores = beforeScoreCount.stream().map(
//                    assessmentQuestionScore -> new AssessmentQuestionScoreRes.QuestionScore(
//                            assessmentQuestionScore.getAssessmentQuestion().getSequence(),
//                            assessmentQuestionScore.getAssessmentQuestion().getQuestion_name(),
//                            assessmentQuestionScore.getScore()
//                    )
//            ).collect(Collectors.toList());
//        }
//
//        List<AssessmentQuestionScore> afterScoreCount = assessmentQuestionScoreRepository.findScoresByScoreCount(current_score_count);
//        afterQuestionScores = afterScoreCount.stream().map(
//                assessmentQuestionScore -> new AssessmentQuestionScoreRes.QuestionScore(
//                        assessmentQuestionScore.getAssessmentQuestion().getSequence(),
//                        assessmentQuestionScore.getAssessmentQuestion().getQuestion_name(),
//                        assessmentQuestionScore.getScore()
//                )
//        ).collect(Collectors.toList());
//
//        int total_score = 0;
//        for(int i = 0 ; i < afterScoreCount.size() ; i++){
//            total_score += afterScoreCount.get(i).getScore();
//        }
//        Double avg_score = ((double)total_score/afterScoreCount.size())*2;
//
//        return new ResponseBody(new AssessmentQuestionScoreRes.getQuestionScore(
//                findScript.getScore_count(),avg_score,beforeQuestionScores,afterQuestionScores
//        ));
//    }
}
