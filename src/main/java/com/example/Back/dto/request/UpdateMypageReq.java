package com.example.Back.dto.request;

import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMypageReq {
    @Size(min = 1, max = 30, message = "이름이 입력되지 않았거나 너무 긴 이름입니다.")
    private String nickname;

    @Size(min = 1, max = 150, message = "소개말이 입력되지 않았거나 너무 긴 소개말입니다.")
    private String introduction;

}
