package com.example.Back.service;

import com.example.Back.domain.AssessmentQuestion;
import com.example.Back.domain.AssessmentQuestionScore;
import com.example.Back.domain.Script;
import com.example.Back.dto.request.AssessmentQuestionScoreReq;
import com.example.Back.dto.response.AssessmentQuestionScoreRes;
import com.example.Back.dto.response.ResponseBody;
import com.example.Back.exception.CustomException;
import com.example.Back.exception.ErrorCode;
import com.example.Back.repository.AssessmentQuestionRepository;
import com.example.Back.repository.AssessmentQuestionScoreRepository;
import com.example.Back.repository.ScriptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AssessmentQuestionScoreService {

    private final ScriptRepository scriptRepository;
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
    public ResponseBody<AssessmentQuestionScoreRes.createQuestionScore> createQuestionScore(
            Long script_id, AssessmentQuestionScoreReq.createQuestionScore request) {
        Script findScript = findScript(script_id);
        List<AssessmentQuestion> findAllQuestion = findAllQuestion_SubSequence0();
        List<AssessmentQuestionScoreReq.QuestionScore> questionScores = request.getQuestionScores();

        List<AssessmentQuestionScore> assessmentQuestionScoreList = new ArrayList<>();
        Integer current_score_count = findScript.getScore_count() + 1;
        for(AssessmentQuestion assessmentQuestion:findAllQuestion){
            int count = 0;
            int score = 0;
            for(int i = 0 ; i<questionScores.size();i++){
                if(questionScores.get(i).getQuestion_sequence().equals(assessmentQuestion.getSequence())) {
                    score += questionScores.get(i).getScore();
                    count++;
                }
            }

            Double score_avg = (double)(score)/count;
            AssessmentQuestionScore assessmentQuestionScore =
                    AssessmentQuestionScore.builder()
                            .score(score_avg)
                            .script(findScript)
                            .assessmentQuestion(assessmentQuestion)
                            .score_count(current_score_count)
                            .build();
            assessmentQuestionScoreList.add(assessmentQuestionScore);
        }

        findScript.setAssessmentQuestionScoreList(assessmentQuestionScoreList);
        findScript.setScore_count(current_score_count);
        assessmentQuestionScoreRepository.saveAll(assessmentQuestionScoreList);

        return new ResponseBody(new AssessmentQuestionScoreRes.createQuestionScore(findScript.getScore_count(),
                assessmentQuestionScoreList.stream().map(
                        assessmentQuestionScore -> new AssessmentQuestionScoreRes.QuestionScore(
                                assessmentQuestionScore.getAssessmentQuestion().getSequence(),
                                assessmentQuestionScore.getAssessmentQuestion().getQuestion_name(),
                                assessmentQuestionScore.getScore()
                        )
                ).collect(Collectors.toList())
        ));
    }

    public ResponseBody<AssessmentQuestionScoreRes.getQuestionScore> getQuestionScore(Long script_id) {

        Script findScript = findScript(script_id);

        Integer current_score_count = findScript.getScore_count();

        List<AssessmentQuestionScoreRes.QuestionScore> beforeQuestionScores = new ArrayList<>();
        List<AssessmentQuestionScoreRes.QuestionScore> afterQuestionScores = new ArrayList<>();

        if(current_score_count == 1){
            beforeQuestionScores = null;
        }else{
            List<AssessmentQuestionScore> beforeScoreCount = assessmentQuestionScoreRepository.findScoresByScoreCount(current_score_count - 1);
            beforeQuestionScores = beforeScoreCount.stream().map(
                    assessmentQuestionScore -> new AssessmentQuestionScoreRes.QuestionScore(
                            assessmentQuestionScore.getAssessmentQuestion().getSequence(),
                            assessmentQuestionScore.getAssessmentQuestion().getQuestion_name(),
                            assessmentQuestionScore.getScore()
                    )
            ).collect(Collectors.toList());
        }

        List<AssessmentQuestionScore> afterScoreCount = assessmentQuestionScoreRepository.findScoresByScoreCount(current_score_count);
        afterQuestionScores = afterScoreCount.stream().map(
                assessmentQuestionScore -> new AssessmentQuestionScoreRes.QuestionScore(
                        assessmentQuestionScore.getAssessmentQuestion().getSequence(),
                        assessmentQuestionScore.getAssessmentQuestion().getQuestion_name(),
                        assessmentQuestionScore.getScore()
                )
        ).collect(Collectors.toList());

        int total_score = 0;
        for(int i = 0 ; i < afterScoreCount.size() ; i++){
            total_score += afterScoreCount.get(i).getScore();
        }
        Double avg_score = ((double)total_score/afterScoreCount.size())*2;

        return new ResponseBody(new AssessmentQuestionScoreRes.getQuestionScore(
                findScript.getScore_count(),avg_score,beforeQuestionScores,afterQuestionScores
        ));
    }
}
