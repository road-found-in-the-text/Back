package com.example.Back.service;

import com.example.Back.domain.AssessmentQuestion;
import com.example.Back.domain.AssessmentQuestionScore;
import com.example.Back.domain.AssessmentQuestionView;
import com.example.Back.domain.Script;
import com.example.Back.dto.request.AssessmentQuestionScoreReq;
import com.example.Back.dto.response.AssessmentQuestionScoreRes;
import com.example.Back.dto.response.ResponseBody;
import com.example.Back.exception.CustomException;
import com.example.Back.exception.ErrorCode;
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
