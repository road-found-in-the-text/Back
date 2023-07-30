package com.example.Back.controller;

import com.example.Back.dto.request.ScriptRequestDto;
import com.example.Back.dto.response.ScriptResponseDto;
import com.example.Back.service.ScriptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController      // Json 형태로 객체 데이터를 반환 (@Controller + @ResponseBody)
@RequestMapping("api/v1/script")
@RequiredArgsConstructor
// @Api(tags = {"Script Api"})
public class ScriptController {

    @Autowired
    private final ScriptService scriptService;
    private final ScriptResponseDto scriptResponseDto;
    // private final JwtService jwtService;

    @PostMapping("/new")
    @Operation(summary = "스크립트 작성", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<?> writeScript(@Valid @RequestBody ScriptRequestDto.Register script ){

        return scriptService.writeScript(script);
    }

    @GetMapping("/{id}")
    @Operation(summary = "스크립트 가져오기", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<?> readScript(@PathVariable("id") Long id) {

        return scriptService.getScriptContents(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "스크립트 삭제", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<?> deleteScript(@PathVariable Long id) {
        return scriptService.deleteScript(id);
    }

    // 특정 사용자가 작성한 script 모두 갖고오기
    @GetMapping("/all/me")
    @Operation(summary = "특정 memberId의 사용자 스크립트 모두 가져오기", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<?> readAllWriterScript() {

        return scriptService.getWriterScriptContents();
    }

    // 모든 script 갖고오기
    @GetMapping("/all")
    @Operation(summary = "전체 스크립트 가져오기", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<?> readAllScript() {

        return scriptService.getAllScriptContents();
    }


}
