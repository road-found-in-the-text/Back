package com.example.Back.dto.request;

import com.example.Back.dto.response.ParagraphRes;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Component
public class ScriptRequestDto {
    @Getter
    @Setter
    public static class Register {

        @NotNull(message = "user id는 필수 입력값입니다.")
        private String memberId;

        @NotNull(message = "title은 필수 입력값입니다.")
        private String script_title;

        private ArrayList<ParagraphReq> contents;

    }

    @Getter
    @Setter
    public static class Update {

        @NotNull(message = "title은 필수 입력값입니다.")
        private String title;

        private ArrayList<ParagraphReq> contents;

    }
}
