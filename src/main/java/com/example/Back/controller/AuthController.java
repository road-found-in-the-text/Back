package com.example.Back.controller;

import com.example.Back.domain.Member;
import com.example.Back.domain.TokenReissue;
import com.example.Back.dto.request.AuthReq;
import com.example.Back.dto.response.AuthRes;
import com.example.Back.service.AuthMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthMemberService authMemberService;

    @PostMapping("/login")
    @Operation(summary = "로그인", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<AuthRes> getTokens(@RequestBody AuthReq authRequest) throws NoSuchAlgorithmException {
        AuthRes authResponse = authMemberService.signUpOrLogIn(authRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PatchMapping("/logout")
    public ResponseEntity<Void> logOut (Member member) {
        authMemberService.logout(member);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/withdrawl")
    @Operation(summary = "회원 탈퇴", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<Void> withdrawl (Member member) {
        authMemberService.withdrawl(member);
        return ResponseEntity.ok().build();
    }
}
