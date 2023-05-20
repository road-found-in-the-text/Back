package com.example.Back.service;

import com.example.Back.domain.AssessmentMemo;
import com.example.Back.domain.Script;
import com.example.Back.dto.request.AssessmentMemoReq;
import com.example.Back.dto.response.AssessmentMemoRes;
import com.example.Back.dto.response.ResponseBody;
import com.example.Back.exception.CustomException;
import com.example.Back.exception.ErrorCode;
import com.example.Back.repository.AssessmentMemoRepository;
import com.example.Back.repository.ScriptRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AssessmentMemoService {

    private final ScriptRepository scriptRepository;
    private final AssessmentMemoRepository assessmentMemoRepository;


    private Script findScript(Long script_id){
        return scriptRepository.findById(script_id).orElseThrow(
                () -> new CustomException(ErrorCode.SCRIPT_NOT_FOUND)
        );
    }

    @Transactional
    public ResponseBody<String> createMemo(Long script_id, AssessmentMemoReq.createMemo request) {
        Script script = findScript(script_id);
        AssessmentMemo assessmentMemo = AssessmentMemo.builder()
                .script(script)
                .score_count(script.getScore_count())
                .memo(request.getMemo())
                .build();
        assessmentMemoRepository.save(assessmentMemo);
        return new ResponseBody("메모 생성이 성공적으로 완료되었습니다.");
    }

    public ResponseBody<AssessmentMemoRes.getMemo> getMemo(Long script_id) {
        Script script = findScript(script_id);
        List<AssessmentMemo> assessmentMemos = assessmentMemoRepository.findMemoByScriptIdOrderByScoreCount(script_id);
        return new ResponseBody(new AssessmentMemoRes.getMemo(assessmentMemos.stream().map(
                assessmentMemo -> new AssessmentMemoRes.MemoInfo(assessmentMemo.getScore_count(),assessmentMemo.getMemo())
        ).collect(Collectors.toList())));
    }
}
