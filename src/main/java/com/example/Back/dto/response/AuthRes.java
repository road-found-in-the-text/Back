package com.example.Back.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthRes {
    private String accessToken;
    private String refreshToken;
    private Boolean isNewMember;
    private Boolean userSettingDone;
    private Long userId;

}
