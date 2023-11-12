package com.example.erp.faq.service;


import com.example.erp.faq.dto.ProblemDto;
import com.example.erp.faq.entity.ProblemEntity;
import com.example.erp.faq.repository.ProblemRepository;
import com.example.erp.member.entity.MemberEntity;
import com.example.erp.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final MemberRepository memberRepository;


    //리스트띄어주기
    public List<ProblemDto> getAllProblems() {
        List<ProblemEntity> problemEntities = problemRepository.findAll();
        return problemEntities.stream()
                .map(problemEntity -> {
                    ProblemDto problemDto = ProblemDto.toProblemDto(problemEntity);
                    problemDto.setWriter(problemEntity.getUser().getUserName());
                    return problemDto;
                })
                .collect(Collectors.toList());
    }

    //저장용
    @Transactional
    public void save(ProblemDto problemDto) throws IOException {
        String writerId = problemDto.getWriter();
        //작성자 정보 찾기
        MemberEntity writer = memberRepository.findByUserId(writerId).orElse(null);
        ProblemEntity problemEntity = ProblemEntity.toSaveEntity(problemDto, writer);
        problemRepository.save(problemEntity); //entity값으로 반환
    }



    //게시글 자세히보기
    @Transactional
    public ProblemDto findById(long id) {
        Optional<ProblemEntity> optionalProblemEntity = problemRepository.findById(id);
        if (optionalProblemEntity.isPresent()){
            ProblemEntity problemEntity = optionalProblemEntity.get();
            ProblemDto problemDto = ProblemDto.toProblemDto(problemEntity);
            problemDto.setWriter(problemEntity.getUser().getUserName());
            return problemDto;
        } else {
            return null;
        }
    }

    @Transactional
    public void update(Long id, ProblemDto problemDto) {
        ProblemEntity problemEntity = problemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("공지사항: " + id + " 를 찾을수 없습니다."));
        problemEntity.update(problemDto.getProblemTitle(),problemDto.getProblemMemo());
    }
}
