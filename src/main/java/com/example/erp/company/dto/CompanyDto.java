package com.example.erp.company.dto;

import com.example.erp.company.entity.CompanyEntity;
import com.example.erp.member.entity.MemberEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class CompanyDto {
    private long id;
    private String companyName;
    private String companyVip;
    private String companyTel;
    private String status;
    private int moneyRecieve;
    private int money;

    public static CompanyDto toCompanyDto(CompanyEntity companyEntity) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(companyEntity.getId());
        companyDto.setCompanyName(companyEntity.getCompanyName());
        companyDto.setCompanyVip(companyEntity.getCompanyVip());
        companyDto.setCompanyTel(companyEntity.getCompanyTel());
        companyDto.setStatus(companyEntity.getStatus());
        companyDto.setMoneyRecieve(companyEntity.getMoneyRecieve());
        companyDto.setMoney(companyEntity.getMoney());

        return companyDto;
    }

}
