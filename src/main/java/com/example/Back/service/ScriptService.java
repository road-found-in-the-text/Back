package com.example.Back.service;

import com.example.Back.domain.Member;
import com.example.Back.domain.Paragraph;
import com.example.Back.domain.Script;
import com.example.Back.dto.request.ParagraphReq;
import com.example.Back.dto.request.ScriptRequestDto;
import com.example.Back.dto.response.ParagraphRes;
import com.example.Back.dto.response.ScriptResponseDto;
import com.example.Back.exception.ResponseTemplate;
import com.example.Back.repository.ParagraphRepository;
import com.example.Back.repository.ScriptRepository;
import com.example.Back.repository.SocialMemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.example.Back.exception.ResponseTemplateStatus.USER_NOT_FOUND;

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

    private final SocialMemberRepository memberRepository;


    // private final MemoService memoService;

    // private final RecordService recordService;
    private final EntityManager em;


    public ResponseEntity<?> writeScript(ScriptRequestDto.Register script1) {

        ArrayList<ParagraphReq> contents = script1.getContents();
        int cnt_list = contents.size();

        List<Paragraph> paragraphs_list = new ArrayList<Paragraph>();
        Optional<Member> cur_member = memberRepository.findBySocialId(script1.getMemberId());

        if (cur_member.isPresent()) {
            Member member = cur_member.get();

            Script script=Script.builder()
                    .memberId(member)
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

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseTemplate<>(USER_NOT_FOUND));
    }


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
