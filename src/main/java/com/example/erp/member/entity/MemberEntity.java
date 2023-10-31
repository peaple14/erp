package com.example.erp.member.entity;


import com.example.erp.member.dto.MemberDto;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "member_table")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String userId;

    @Column
    private String userPass;

    @Column
    private String userName;

    @Column
    private String userauthority; //권한

    //사원관리용도.
//    public static MemberEntity toMemberEntity(MemberDto memberDTO){
//        MemberEntity memberEntity = new MemberEntity();
//        memberEntity.setUserId(memberDTO.getUserId());
//        memberEntity.setUserPass(memberDTO.getUserPass());
//        memberEntity.setUserName(memberDTO.getUserName());
//        memberEntity.setUserauthority(memberDTO.getUserauthority());
//        return memberEntity;
//    }
}
