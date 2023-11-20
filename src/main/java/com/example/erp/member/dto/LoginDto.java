package com.example.erp.member.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class LoginDto {

    private String userId;
    private String userPass;


}
