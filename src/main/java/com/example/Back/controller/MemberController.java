package com.example.Back.controller;

import com.example.Back.domain.CurrentUser;
import com.example.Back.domain.Member;
import com.example.Back.dto.request.UpdateMypageReq;
import com.example.Back.dto.request.UpdateNickNameReq;
import com.example.Back.dto.response.MemberRes;
import com.example.Back.dto.response.UpdateMypageRes;
import com.example.Back.service.AuthMemberService;
import com.example.Back.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/members")
public class MemberController {

    @Autowired
    private final TokenService tokenService;

    @Autowired
    private final AuthMemberService authMemberService;

    public MemberController(TokenService tokenService, AuthMemberService authMemberService) {
        this.tokenService = tokenService;
        this.authMemberService = authMemberService;
    }

    @PutMapping("/profile") @ResponseBody
    @Operation(summary = "닉네임 및 자기소개 변경", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<UpdateMypageRes> updateUserNickname (@RequestBody UpdateMypageReq updateMypageReq) {
        UpdateMypageRes updateMypageRes = authMemberService.updateProfile(updateMypageReq);
        return ResponseEntity.ok(updateMypageRes);
    }

    @PostMapping("/nickname") @ResponseBody
    @Operation(summary = "닉네임 할당", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<String> initializeNickname (@RequestBody UpdateNickNameReq updateNickNameReq) {
        try {
            String nickName = updateNickNameReq.getNickname();
            System.out.println(nickName);
            String memberId = tokenService.getSocialId();
            authMemberService.updateNickName(memberId, nickName);
            return ResponseEntity.ok(nickName);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/mypage")
    @Operation(summary = "회원 조회", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<MemberRes> getMypage () {
        String memberId = tokenService.getSocialId();
        MemberRes memberRes = authMemberService.getMemberInfo(memberId);
        return ResponseEntity.ok(memberRes);
    }
}
