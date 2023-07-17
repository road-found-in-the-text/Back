package com.example.Back.controller;

import com.example.Back.domain.AssessmentMemo;
import com.example.Back.dto.request.AssessmentMemoReq;
import com.example.Back.dto.response.AssessmentMemoRes;
import com.example.Back.dto.response.ResponseBody;
import com.example.Back.service.AssessmentMemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/script/{script-id}/memo")
@RequiredArgsConstructor
@Tag(name = "평가에 대한 메모 작성 및 조회", description = "평가 후 메모를 작성하고 메모를 다 가지고 올 수 있다.")
public class AssessmentMemoController {

    private final AssessmentMemoService assessmentMemoService;

    @PostMapping("")
    @Operation(summary = "메모 생성", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseBody<String> createMemo(@PathVariable(name = "script-id")Long script_id,
                                                                 @RequestBody AssessmentMemoReq.createMemo request){
        return assessmentMemoService.createMemo(script_id,request);
    }

    @GetMapping("")
    @Operation(summary = "메모 가져오기", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseBody<AssessmentMemoRes.getMemo> getMemo(@PathVariable(name = "script-id")Long script_id){
        return assessmentMemoService.getMemo(script_id);
    }
}
