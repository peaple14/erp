package com.example.erp.faq.entity;


import com.example.erp.faq.dto.ProblemDto;
import com.example.erp.member.entity.MemberEntity;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Data
@Table(name="problem_table")
public class ProblemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String problemTitle;

    @Column
    private String problemMemo;


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdTime;

    @ManyToOne
    @JoinColumn(name = "member_userId")
    private MemberEntity user;

    public static ProblemEntity toSaveEntity(ProblemDto problemDto,MemberEntity writer){
        ProblemEntity problemEntity = new ProblemEntity();
        problemEntity.setProblemTitle(problemDto.getProblemTitle());
        problemEntity.setProblemMemo(problemDto.getProblemMemo());
        problemEntity.setUser(writer);
        return problemEntity;
    }


    //더티체킹 수정용.
    public void update(String problemTitle, String problemMemo) {
        System.out.println("시작");
        if (problemTitle != null) {
            this.problemTitle = problemTitle;
        }
        if (problemMemo != null) {
            this.problemMemo = problemMemo;
        }
    }


}
