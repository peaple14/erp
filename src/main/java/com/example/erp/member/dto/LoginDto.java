package com.example.erp.member.dto;


import com.example.erp.member.entity.MemberEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class LoginDto {

    private String userId;
    private String userPass;

//    public static LoginDto tologinDto(MemberEntity memberEntity){
//        LoginDto loginDto = new LoginDto();
//        loginDto.setUserId(memberEntity.getUserId());
//        loginDto.setUserPass(memberEntity.getUserPass());
//        return loginDto;
//    }
}
