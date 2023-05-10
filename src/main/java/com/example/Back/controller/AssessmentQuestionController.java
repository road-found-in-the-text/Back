package com.example.Back.controller;

import com.example.Back.dto.request.AssessmentQuestionReq;
import com.example.Back.dto.response.AssessmentQuestionRes;
import com.example.Back.dto.response.ResponseBody;
import com.example.Back.service.AssessmentQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController      // Json 형태로 객체 데이터를 반환 (@Controller + @ResponseBody)
@RequestMapping("api/v1/assessment-question")
@RequiredArgsConstructor
@Tag(name = "평가 항목", description = "평가 항목 관련 api 입니다.")
public class AssessmentQuestionController {

    private final AssessmentQuestionService assessmentQuestionService;

    @Operation(summary = "평가 항목 모두 가져오기")
    @ApiResponse(description = "평가 항목 전체 가져오기")
    @GetMapping("")
    public ResponseBody<AssessmentQuestionRes.getQuestion> getQuestion(){
        return assessmentQuestionService.getQuestion();
    }

    @Operation(summary = "평가 항목 저장")
    @ApiResponse(description = "평가 항목 전체 db에 저장하기")
    @PostMapping("")
    public ResponseBody<String> createQuestion(@Valid @RequestBody AssessmentQuestionReq.createQuestion request){
        return assessmentQuestionService.createQuestion(request);
    }

    @Operation(summary = "평가 항목 전체 삭제")
    @ApiResponse(description = "평가 항목 전체 삭제")
    @DeleteMapping("")
    public ResponseBody<String> deleteQuestion(){
        return assessmentQuestionService.deleteQuestion();
    }

}
