package com.example.Back.controller;

import com.example.Back.domain.AssessmentQuestionScore;
import com.example.Back.domain.Script;
import com.example.Back.dto.request.AssessmentQuestionScoreReq;
import com.example.Back.dto.response.AssessmentQuestionScoreRes;
import com.example.Back.dto.response.ResponseBody;
import com.example.Back.service.AssessmentQuestionScoreService;
import com.example.Back.service.AssessmentQuestionViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/script/{script-id}/score")
@RequiredArgsConstructor
@Tag(name = "평가 항목 점수", description = "평가 항목 점수 관련 api 입니다.")
public class AssessmentQuestionScoreController {

    private final AssessmentQuestionScoreService assessmentQuestionScoreService;
    private final AssessmentQuestionViewService assessmentQuestionViewService;

    @Operation(summary = "평가 항목 점수 db에 저장")
    @ApiResponse(description = "평가 항목에 대한 점수를 db에 저장하기")
    @PostMapping("")
    public ResponseBody<AssessmentQuestionScoreRes.getQuestionScore> createQuestionScore(
            @PathVariable(name = "script-id") Long script_id, @RequestBody AssessmentQuestionScoreReq.createQuestionScore request){
        return assessmentQuestionScoreService.createQuestionScore(script_id, request);
//        return assessmentQuestionViewService.createQuestionView(script);
    }
//
//    @Operation(summary = "평가 항목 점수 db에 저장")
//    @ApiResponse(description = "평가 항목에 대한 점수를 db에 저장하기")
//    @GetMapping("")
//    public ResponseBody<AssessmentQuestionScoreRes.getQuestionScore> getQuestionScore(
//            @PathVariable(name = "script-id") Long script_id){
//        return assessmentQuestionScoreService.getQuestionScore(script_id);
//    }
}
