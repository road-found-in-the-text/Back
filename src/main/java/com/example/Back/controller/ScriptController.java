package com.example.Back.controller;

import com.example.Back.dto.request.ScriptRequestDto;
import com.example.Back.dto.response.ScriptResponseDto;
import com.example.Back.service.ScriptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController      // Json 형태로 객체 데이터를 반환 (@Controller + @ResponseBody)
@RequestMapping("/script")
@RequiredArgsConstructor
// @Api(tags = {"Script Api"})
public class ScriptController {

    @Autowired
    private final ScriptService scriptService;
    private final ScriptResponseDto scriptResponseDto;
    // private final JwtService jwtService;

    @PostMapping("/new")
    public ResponseEntity<?> writeScript(@Valid @RequestBody ScriptRequestDto.Register script ){

        return scriptService.writeScript(script);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readScript(@PathVariable("id") Long id) {

        return scriptService.getScriptContents(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteScript(@PathVariable Long id) {
        return scriptService.deleteScript(id);
    }


}
