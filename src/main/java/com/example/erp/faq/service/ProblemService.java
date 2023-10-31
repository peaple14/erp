package com.example.erp.faq.service;


import com.example.erp.faq.dto.NoticeDto;
import com.example.erp.faq.dto.ProblemDto;
import com.example.erp.faq.entity.NoticeEntity;
import com.example.erp.faq.entity.ProblemEntity;
import com.example.erp.faq.repository.NoticeRepository;
import com.example.erp.faq.repository.ProblemRepository;
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


    //리스트띄어주기
    public List<ProblemDto> getAllProblems() {
        List<ProblemEntity> problemEntities = problemRepository.findAll();
        return problemEntities.stream()
                .map(ProblemDto::toProblemDto)
                .collect(Collectors.toList());
    }

    //저장용
    @Transactional
    public void save(ProblemDto problemDto) throws IOException {
        ProblemEntity problemEntity = ProblemEntity.toSaveEntity(problemDto);
        problemRepository.save(problemEntity); //entity값으로 반환
    }



    //게시글 자세히보기
    @Transactional
    public ProblemDto findById(long id) {
        Optional<ProblemEntity> optionalProblemEntity = problemRepository.findById(id);
        if (optionalProblemEntity.isPresent()){
            ProblemEntity problemEntity = optionalProblemEntity.get();
            ProblemDto problemDto = ProblemDto.toProblemDto(problemEntity);
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
