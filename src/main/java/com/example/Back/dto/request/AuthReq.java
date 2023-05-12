package com.example.Back.dto.request;

import com.example.Back.domain.LoginType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthReq {

    private String accessToken;
    private LoginType loginType;
}
