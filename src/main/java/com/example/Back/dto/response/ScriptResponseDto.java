package com.example.Back.dto.response;

import com.example.Back.domain.Paragraph;
import com.example.Back.domain.Script;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScriptResponseDto {

    @Getter
    @Builder
    private static class Body {

        private String result;

        // private Long memberId;
        private Long script_id;
        private String message;

    }

    @Getter
    @Builder
    private static class ScriptBody {

        private String result;
        // private Long memberId;
        private Long script_id;

        private String script_title;
        private List<ParagraphRes> content;
        private int content_cnt;

        // pattern = "dd-MM-yyyy hh:mm:ss a"
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd hh:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime modifiedDate;

    }

    @Getter
    @Builder
    private static class ScriptListBody {

        private String result;
        private List<ScriptRes> scripts;
        private int count;
        // private LocalDateTime modifiedDate;

    }

    // script 최초 생성
    public ResponseEntity<?> scriptCreateSuccess(Script script) {

        Body body = Body.builder()
                .result("success")
                // .memberId(script.getMemberId().getId())
                .script_id(script.getScriptId())
                .message("script가 성공적으로 저장되었습니다.")
                .build();
        return ResponseEntity.ok(body);
    }

    // script 최초 생성
    public ResponseEntity<?> scriptDeleteSuccess(Script script) {

        Body body = Body.builder()
                .result("success")
                // .memberId(script.getMemberId().getId())
                .script_id(script.getScriptId())
                .message("script가 성공적으로 삭제되었습니다.")
                .build();
        return ResponseEntity.ok(body);
    }

    // script 전체 내용 보여주기
    public ResponseEntity<?> scriptSuccess(Script script) {

        List<Paragraph> paragraphs = script.getParagraphs();
        List<ParagraphRes> contents = new ArrayList<ParagraphRes>();

        for (int idx=0; idx< paragraphs.size(); idx++) {
            Paragraph cur_paragraph = paragraphs.get(idx);
            ParagraphRes info = new ParagraphRes();

            info.setParagraph_id(cur_paragraph.getIdx());
            info.setParagraph_title(cur_paragraph.getTitle());
            info.setParagraph_content(cur_paragraph.getContents());

            contents.add(info);
        }


        ScriptBody body = ScriptBody.builder()
                .result("success")
                //.memberId(script.getMemberId().getId())
                .script_id(script.getScriptId())
                .script_title(script.getTitle())
                .content_cnt(script.getCnt())
                .content(contents)
                // .paragraphList(script.getParagraphs())
                // .createdDate(script.getCreatedDate())
                .modifiedDate(script.getModifiedDate())
                .build();
        return ResponseEntity.ok(body);
    }

    public ResponseEntity<?> scriptAllSuccess(List<Script> script_list) {

        // List<Paragraph> paragraphs = script.getParagraphs();
        List<ScriptRes> scripts = new ArrayList<ScriptRes>();

        for (int idx=0; idx< script_list.size(); idx++) {
            Script cur_script = script_list.get(idx);
            ScriptRes info = new ScriptRes();

            info.setScript_id(cur_script.getScriptId());
            info.setScript_title(cur_script.getTitle());
            info.setContent(cur_script.getParagraphs().get(0).getContents());

            scripts.add(info);
        }


        ScriptListBody body = ScriptListBody.builder()
                .result("success")
                //.memberId(script.getMemberId().getId())
                .scripts(scripts)
                .count(scripts.size())
                // .modifiedDate(script.getCnt())
                .build();
        return ResponseEntity.ok(body);
    }

    /*
    public ResponseEntity<?> success(Script script) {

        List<Paragraph> paragraphList = new ArrayList<>();
        paragraphList=script.getParagraphs();

        //Member script_member=memberRepository.getUser(script.getMemberId());

        String firestScriptContent;

        if (paragraphList.size()!=0) {
            Paragraph firstP= paragraphList.get(0);
            firestScriptContent=firstP.getContents();
        } else {
            firestScriptContent="";
        }


        Body body = Body.builder()
                .result("success")
                //.memberId(script.getMemberId().getId())
                .scriptId(script.getScriptId())
                .title(script.getTitle())
                .contents(firestScriptContent)
                //.type(script.getType())
                .createdDate(script.getCreatedDate())
                .modifiedDate(script.getModifiedDate())
                .build();
        return ResponseEntity.ok(body);
    }
    */

}
