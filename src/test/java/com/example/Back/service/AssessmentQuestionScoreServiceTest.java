package com.example.Back.service;

import com.example.Back.domain.AssessmentQuestion;
import com.example.Back.domain.AssessmentQuestionScore;
import com.example.Back.domain.Script;
import com.example.Back.dto.request.AssessmentQuestionScoreReq;
import com.example.Back.repository.AssessmentQuestionRepository;
import com.example.Back.repository.AssessmentQuestionScoreRepository;
import com.example.Back.repository.ScriptRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class AssessmentQuestionScoreServiceTest {


    @Autowired
    private ScriptRepository scriptRepository;

    @Autowired
    private AssessmentQuestionRepository assessmentQuestionRepository;

    @Autowired
    private AssessmentQuestionScoreRepository assessmentQuestionScoreRepository;

    @Test
    public void subSequece_0_인_질문만_가져옴() throws Exception{
        //given
        Script init1 = init();
        //when
        List<AssessmentQuestion> allSub_sequence0 = assessmentQuestionRepository.findAllSub_Sequence0();
        //then

        for(int i =0 ; i < allSub_sequence0.size(); i++){
            System.out.println(allSub_sequence0.get(i).getQuestion_name());
            System.out.println(allSub_sequence0.get(i).getSub_sequence());
        }
    }

    @Test
    public void 질문_점수_저장() throws Exception{
        //given
        Script script = init();
        List<AssessmentQuestionScoreReq.QuestionScore> questionScores = new ArrayList<>();
        AssessmentQuestionScoreReq.QuestionScore questionScore1_1 = new AssessmentQuestionScoreReq.QuestionScore(1, 3);
        AssessmentQuestionScoreReq.QuestionScore questionScore1_2 = new AssessmentQuestionScoreReq.QuestionScore(1, 5);
        AssessmentQuestionScoreReq.QuestionScore questionScore1_3 = new AssessmentQuestionScoreReq.QuestionScore(1, 4);
        AssessmentQuestionScoreReq.QuestionScore questionScore2_1 = new AssessmentQuestionScoreReq.QuestionScore(2, 3);
        AssessmentQuestionScoreReq.QuestionScore questionScore2_2 = new AssessmentQuestionScoreReq.QuestionScore(2, 4);
        AssessmentQuestionScoreReq.QuestionScore questionScore2_3 = new AssessmentQuestionScoreReq.QuestionScore(2, 3);
        AssessmentQuestionScoreReq.QuestionScore questionScore3_1 = new AssessmentQuestionScoreReq.QuestionScore(3, 3);
        AssessmentQuestionScoreReq.QuestionScore questionScore3_2 = new AssessmentQuestionScoreReq.QuestionScore(3, 5);

        questionScores.add(questionScore1_1);
        questionScores.add(questionScore1_2);
        questionScores.add(questionScore1_3);
        questionScores.add(questionScore2_1);
        questionScores.add(questionScore2_2);
        questionScores.add(questionScore2_3);
        questionScores.add(questionScore3_1);
        questionScores.add(questionScore3_2);

        AssessmentQuestionScoreReq.createQuestionScore createQuestionScore = new AssessmentQuestionScoreReq.createQuestionScore(questionScores);

        List<AssessmentQuestion> allSub_sequence0 = assessmentQuestionRepository.findAllSub_Sequence0();

        //when

        List<AssessmentQuestionScore> assessmentQuestionScoreList = new ArrayList<>();

        for(AssessmentQuestion assessmentQuestion:allSub_sequence0){
            int score = 0;
            int count = 0;
            System.out.println(assessmentQuestion.getQuestion_name());
            for(int i = 0 ; i<questionScores.size();i++){
                if(questionScores.get(i).getQuestion_sequence().equals(assessmentQuestion.getSequence())) {
                    System.out.println("i : "+i);
                    System.out.println("score : " + score);
                    System.out.println("questionScores.get(i).getScore() : " + questionScores.get(i).getScore());
                    score += questionScores.get(i).getScore();
                    count++;
                }
            }
            System.out.println("count : " + count);
            System.out.println("score : " + score);

            Double score_avg = (double)(score)/count;
            AssessmentQuestionScore assessmentQuestionScore =
                    AssessmentQuestionScore.builder()
                            .score(score_avg)
                            .script(script)
                            .assessmentQuestion(assessmentQuestion)
                            .build();
            assessmentQuestionScoreList.add(assessmentQuestionScore);
        }


        //then

        for(int i = 0 ; i < assessmentQuestionScoreList.size() ; i++){
            System.out.println(assessmentQuestionScoreList.get(i).getAssessmentQuestion().getQuestion_name());
            System.out.println(assessmentQuestionScoreList.get(i).getScore());
        }

        script.setAssessmentQuestionScoreList(assessmentQuestionScoreList);
        assessmentQuestionScoreRepository.saveAll(assessmentQuestionScoreList);
    }

    private Script init(){
        Script script = Script.builder()
                .scriptId(1L)
                .cnt(0)
                .deleted(false)
                .build();
        scriptRepository.saveAndFlush(script);

        AssessmentQuestion assessmentQuestion1 = new AssessmentQuestion(1,0,"분석력");
        AssessmentQuestion assessmentQuestion1_1 = new AssessmentQuestion(1,1,"분석력이 좋은가?");
        AssessmentQuestion assessmentQuestion1_2 = new AssessmentQuestion(1,2,"분석력이 나쁜가?");
        AssessmentQuestion assessmentQuestion1_3 = new AssessmentQuestion(1,3,"분석력이 좋은 사람인가?");

        AssessmentQuestion assessmentQuestion2 = new AssessmentQuestion(2,0,"이해력");
        AssessmentQuestion assessmentQuestion2_1 = new AssessmentQuestion(2,1,"이해력 좋은가?");
        AssessmentQuestion assessmentQuestion2_2 = new AssessmentQuestion(2,2,"이해력 나쁜가?");
        AssessmentQuestion assessmentQuestion2_3 = new AssessmentQuestion(2,3,"이해력 좋은 사람인가?");

        AssessmentQuestion assessmentQuestion3 = new AssessmentQuestion(3,0,"판단력");
        AssessmentQuestion assessmentQuestion3_1 = new AssessmentQuestion(3,1,"판단력 좋은가?");
        AssessmentQuestion assessmentQuestion3_2 = new AssessmentQuestion(3,2,"판단력 나쁜가?");

        assessmentQuestionRepository.saveAndFlush(assessmentQuestion1);
        assessmentQuestionRepository.saveAndFlush(assessmentQuestion1_1);
        assessmentQuestionRepository.saveAndFlush(assessmentQuestion1_2);
        assessmentQuestionRepository.saveAndFlush(assessmentQuestion1_3);
        assessmentQuestionRepository.saveAndFlush(assessmentQuestion2);
        assessmentQuestionRepository.saveAndFlush(assessmentQuestion2_1);
        assessmentQuestionRepository.saveAndFlush(assessmentQuestion2_2);
        assessmentQuestionRepository.saveAndFlush(assessmentQuestion2_3);
        assessmentQuestionRepository.saveAndFlush(assessmentQuestion3);
        assessmentQuestionRepository.saveAndFlush(assessmentQuestion3_1);
        assessmentQuestionRepository.saveAndFlush(assessmentQuestion3_2);

        return script;
    }
}
