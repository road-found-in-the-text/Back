package com.example.Back.controller;


import com.example.Back.dto.response.AssessmentQuestionScoreRes;
import com.example.Back.dto.response.AssessmentQuestionViewRes;
import com.example.Back.dto.response.ResponseBody;
import com.example.Back.service.AssessmentQuestionViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/script/{script-id}/score")
@RequiredArgsConstructor
@Tag(name = "평가 항목 점수 보여주는 controller", description = "평가 항목 점수를 보여주기 위한 api입니다.")
public class AssessmentQuestionViewController {

    private final AssessmentQuestionViewService assessmentQuestionViewService;

    @Operation(summary = "평가 항목 점수 계산해서 user에게 보여줌", security = @SecurityRequirement(name = "bearer-key"))
    @ApiResponse(description = "평가 항목 점수 계산해서 user에게 보여줌")
    @GetMapping("")
    public ResponseBody<AssessmentQuestionViewRes.getQuestionScore> getQuestionScore(
            @PathVariable(name = "script-id") Long script_id){
        return assessmentQuestionViewService.getQuestionScore(script_id);
    }
}
