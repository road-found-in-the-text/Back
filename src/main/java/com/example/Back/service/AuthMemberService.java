package com.example.Back.service;


import com.example.Back.domain.*;
import com.example.Back.dto.request.AuthReq;
import com.example.Back.dto.response.AuthRes;
import com.example.Back.dto.response.MemberRes;
import com.example.Back.repository.SocialMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthMemberService {
    private static final Logger logger = LoggerFactory.getLogger(Exception.class);

    private final SocialMemberRepository socialMemberRepository;
    private final TokenService tokenService;
    private final KakaoService clientKakao;
    private final AppleService clientApple;

    public AuthRes signUpOrLogIn(AuthReq authRequest) throws NoSuchAlgorithmException {
        Member member;
        if (authRequest.getLoginType() == LoginType.KAKAO) {
            member = getKakaoProfile(authRequest.getAccessToken());
        } else {
            member = getAppleProfile(authRequest.getAccessToken());
        }

        Token token = tokenService.generateToken(member.getSocialId(), "USER");

        System.out.println("accessToken!!!!");
        System.out.println(token.getAccessToken());
        boolean isNewMember = false;
        boolean isUserSettingDone = false;

        //최초 회원가입 시
        if (socialMemberRepository.findBySocialId(member.getSocialId()).equals(Optional.empty())) {
            member.setTier(Tier.Bronze);
            member.setIntroduction("안녕하세요.");
            socialMemberRepository.save(member);
            System.out.println("accessToken!!!!");
            System.out.println(token.getAccessToken());
            System.out.println(member.getSocialId());

            isNewMember = true;
        } else {
            Optional<Member> currentUser = socialMemberRepository.findBySocialId(member.getSocialId());

            Member thisUser = currentUser.get();
            if(thisUser.getNickName() != null){
                isUserSettingDone = true;
            }
        }

        return AuthRes.builder()
                .isNewMember(isNewMember)
                .accessToken(token.getAccessToken())
                .userSettingDone(isUserSettingDone)
                .build();
    }

    public Member getKakaoProfile(String oauthToken) {
        Member kakaoUser = clientKakao.getMemberData(oauthToken);
        System.out.println("getKakao");
        return kakaoUser;
    }

    public Member getAppleProfile(String oauthToken) throws NoSuchAlgorithmException {
        Member appleUser = clientApple.getMemberData(oauthToken);
        System.out.println("getApple");
        return appleUser;
    }

    public void logout(Member member) {
        // Logout 시 특별히 할 일이 없으므로, refreshToken 삭제 등의 작업은 필요 없습니다.
    }

    @Transactional
    public void withdrawl(Member member){
        member.setSocialId(null);
        socialMemberRepository.save(member);
    }

    public void updateNickName(String memberId, String updateNickname) {
        Member member = socialMemberRepository.findBySocialId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 멤버를 찾을 수 없습니다."));
        System.out.println(member.getNickName());
        member.setNickName(updateNickname);
        socialMemberRepository.save(member);
    }


    public MemberRes getMemberInfo(String memberId) {
        Member member = socialMemberRepository.findBySocialId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 멤버를 찾을 수 없습니다."));

        MemberRes memberRes = new MemberRes();
        memberRes.setImageUrl(member.getImageUrl());
        memberRes.setTier(member.getTier());
        memberRes.setNickName(member.getNickName());
        memberRes.setIntroduction(member.getIntroduction());

        return memberRes;
    }
}
