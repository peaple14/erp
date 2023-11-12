package com.example.erp.member.dto;


import com.example.erp.member.entity.MemberEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class MemberDto {
    private Long id;
    private String userId;
    private String userPass;
    private String userName;
    private String userauthority;//권한

    public static MemberDto toMemberDto(MemberEntity memberEntity){
        MemberDto memberDTO = new MemberDto();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setUserId(memberEntity.getUserId());
        memberDTO.setUserPass(memberEntity.getUserPass());
        memberDTO.setUserName(memberEntity.getUserName());
        return memberDTO;
    }
}
