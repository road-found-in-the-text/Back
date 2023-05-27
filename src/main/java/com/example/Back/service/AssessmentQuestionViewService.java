package com.example.Back.service;

import com.example.Back.domain.AssessmentQuestion;
import com.example.Back.domain.AssessmentQuestionScore;
import com.example.Back.domain.AssessmentQuestionView;
import com.example.Back.domain.Script;
import com.example.Back.dto.request.AssessmentQuestionScoreReq;
import com.example.Back.dto.request.AssessmentQuestionViewReq;
import com.example.Back.dto.response.AssessmentQuestionScoreRes;
import com.example.Back.dto.response.ResponseBody;
import com.example.Back.exception.CustomException;
import com.example.Back.exception.ErrorCode;
import com.example.Back.repository.AssessmentQuestionRepository;
import com.example.Back.repository.AssessmentQuestionScoreRepository;
import com.example.Back.repository.AssessmentQuestionViewRepository;
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
public class AssessmentQuestionViewService {

    private final ScriptRepository scriptRepository;

    private final AssessmentQuestionRepository assessmentQuestionRepository;
    private final AssessmentQuestionScoreRepository assessmentQuestionScoreRepository;
    private final AssessmentQuestionViewRepository assessmentQuestionViewRepository;

    Script findScript(Long script_id){
        return scriptRepository.findById(script_id).orElseThrow(
                () -> new CustomException(ErrorCode.SCRIPT_NOT_FOUND)
        );
    }

    @Transactional
    public ResponseBody<AssessmentQuestionScoreRes.getQuestionScore> createQuestionView(Script findScript) {
        List<AssessmentQuestionView> currentAssessmentQuestionViewList = new ArrayList<>();
        List<AssessmentQuestionView> beforeAssessmentQuestionViewList
                = assessmentQuestionViewRepository.findViewByScoreCount(findScript.getScore_count() - 1);


        List<AssessmentQuestion> allQuestionSub_sequence0 = assessmentQuestionRepository.findAllSub_Sequence0();
        log.info(allQuestionSub_sequence0.toString());
        Map<Integer,AssessmentQuestion> assessmentQuestionMap = new HashMap<>();

        for(AssessmentQuestion assessmentQuestion : allQuestionSub_sequence0){
            assessmentQuestionMap.put(assessmentQuestion.getSequence(),assessmentQuestion);
            log.info(assessmentQuestion.getSequence().toString());
            log.info(String.valueOf(assessmentQuestionMap.get(assessmentQuestion.getSequence())));
        }



        List<AssessmentQuestionViewReq.QuestionGroupBySequenceDto> request =
                assessmentQuestionViewRepository.findSequence_0ByScoreCountGroupByScoreAndCount(findScript.getScore_count());

        Double all_score_ave_sum = 0.;
        int count = 0;

        log.info(request.toString());

        for(AssessmentQuestionViewReq.QuestionGroupBySequenceDto assessmentQuestionViewReq:request){
            log.info(assessmentQuestionViewReq.toString());
            Double avg_score = assessmentQuestionViewReq.getScore_sum() * 1. / assessmentQuestionViewReq.getScoreCount();
            String question_name = assessmentQuestionMap.get(assessmentQuestionViewReq.getSequence()).getQuestion_name();
            AssessmentQuestionView assessmentQuestionView = AssessmentQuestionView.builder()
                    .question_category(question_name)
                    .score(avg_score)
                    .sequence(assessmentQuestionViewReq.getSequence())
                    .score_count(findScript.getScore_count())
                    .script(findScript)
                    .build();
            all_score_ave_sum += avg_score;
            count++;
            currentAssessmentQuestionViewList.add(assessmentQuestionView);
        }

        Double all_score_avg = all_score_ave_sum/count;


        assessmentQuestionViewRepository.saveAll(currentAssessmentQuestionViewList);
        return new ResponseBody(new AssessmentQuestionScoreRes.getQuestionScore(
                findScript.getScore_count(),all_score_avg,
                beforeAssessmentQuestionViewList.stream().map(
                        assessmentQuestionView -> new AssessmentQuestionScoreRes.QuestionScore(
                                assessmentQuestionView.getSequence(),assessmentQuestionView.getQuestion_category(),
                                assessmentQuestionView.getScore()
                        )
                ).collect(Collectors.toList())
                ,currentAssessmentQuestionViewList.stream().map(
                        assessmentQuestionView -> new AssessmentQuestionScoreRes.QuestionScore(
                                assessmentQuestionView.getSequence(),assessmentQuestionView.getQuestion_category(),
                                assessmentQuestionView.getScore()
                        )
                ).collect(Collectors.toList())
        ));
    }
}
