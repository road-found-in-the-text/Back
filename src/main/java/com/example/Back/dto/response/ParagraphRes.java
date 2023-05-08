package com.example.Back.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParagraphRes {
    private int paragraph_id;
    private String paragraph_title;
    private String paragraph_content;
}
