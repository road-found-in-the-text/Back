package com.example.Back.dto.request;


import lombok.*;

import javax.validation.constraints.Size;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateNickNameReq {
    @Size(min = 1, max = 30, message = "이름이 입력되지 않았거나 너무 긴 이름입니다.")
    private String nickName;

}