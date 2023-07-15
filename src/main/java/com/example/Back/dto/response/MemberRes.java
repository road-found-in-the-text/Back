package com.example.Back.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberRes {
    private String imageUrl;
    private String nickName;
    private String introduction;
    private Enum Tier;
}
