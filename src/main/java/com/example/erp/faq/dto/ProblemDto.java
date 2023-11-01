package com.example.erp.faq.dto;


import com.example.erp.faq.entity.ProblemEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ToString
public class ProblemDto {
    private Long id;
    private String problemTitle;
    private String problemMemo;
    private String writer;
    private LocalDateTime problemCreatedTime;



    public static ProblemDto toProblemDto(ProblemEntity problemEntity){
        ProblemDto problemDto = new ProblemDto();;
        problemDto.setId(problemEntity.getId());
        problemDto.setProblemTitle(problemEntity.getProblemTitle());
        problemDto.setProblemCreatedTime(problemEntity.getCreatedTime());
        problemDto.setProblemMemo(problemEntity.getProblemMemo());
        return problemDto;
    }
}
