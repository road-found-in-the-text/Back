package com.example.Back.service;

import com.example.Back.domain.Paragraph;
import com.example.Back.domain.Script;
import com.example.Back.dto.request.ParagraphReq;
import com.example.Back.dto.request.ScriptRequestDto;
import com.example.Back.dto.response.ParagraphRes;
import com.example.Back.dto.response.ScriptResponseDto;
import com.example.Back.repository.ParagraphRepository;
import com.example.Back.repository.ScriptRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScriptService {

    // @Autowired
    // private final MemberRepository memberRepository;
    @Autowired
    private final ScriptRepository scriptRepository;
    @Autowired
    private final ParagraphRepository paragraphRepository;

    private final ScriptResponseDto scriptResponse;


    // private final MemoService memoService;

    // private final RecordService recordService;
    private final EntityManager em;


    public ResponseEntity<?> writeScript(ScriptRequestDto.Register script1) {

        // Member script_member=memberRepository.getUser(script1.getMemberId());
        ArrayList<ParagraphReq> contents = script1.getContents();
        int cnt_list = contents.size();

        List<Paragraph> paragraphs_list = new ArrayList<Paragraph>();

        Script script=Script.builder()
                // .memberId(script_member)
                .title(script1.getScript_title())
                .cnt(cnt_list)
                .deleted(false)
                .build();

        for (int idx=1; idx<=cnt_list; idx++) {
            ParagraphReq paragraph_info = contents.get(idx-1);
            Paragraph paragraph = Paragraph.builder()
                    .script(script)
                    .idx(idx)
                    .title(paragraph_info.getParagraph_title())
                    .contents(paragraph_info.getParagraph_content())
                    .deleted(false)
                    .build();
            paragraphRepository.save(paragraph);

            paragraphs_list.add(paragraph);
        }

        script.setParagraphs(paragraphs_list);
        scriptRepository.save(script);

        return scriptResponse.scriptCreateSuccess(script);
    }

    /*
    @Transactional
    public ResponseEntity<?> deleteScript(Long id) {
        Script toDeleteScript=em.find(Script.class, id);

        List<Paragraph> paragraphs = toDeleteScript.getParagraphs();

        for (int idx=0; idx<toDeleteScript.getCnt(); idx++) {
            paragraphRepository.delete(paragraphs.get(idx));
        }
        scriptRepository.delete(toDeleteScript);

        return scriptResponse.scriptDeleteSuccess(toDeleteScript);
    }
    
     */

    @Transactional(readOnly = true)
    public ResponseEntity<?> getScriptContents(Long scriptId){
        Script script1= em.find(Script.class, scriptId);
        return scriptResponse.scriptSuccess(script1);
    }


    /*
    public List<Script> findByMemberId(Long memberId){
        return  em.createQuery("select s from Script s where s.memberId.id= :id", Script.class)
                .setParameter("id", memberId)
                .getResultList();
    };
     */


}
