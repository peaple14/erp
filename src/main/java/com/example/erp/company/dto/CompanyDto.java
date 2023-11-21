package com.example.erp.company.dto;

import com.example.erp.company.entity.CompanyEntity;
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
    private long money;
    private String postcode; //우편번호
    private String detailAddress; //자세한정보
    private String roadAddress;//도로명주소

    public static CompanyDto toCompanyDto(CompanyEntity companyEntity) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(companyEntity.getId());
        companyDto.setCompanyName(companyEntity.getCompanyName());
        companyDto.setCompanyVip(companyEntity.getCompanyVip());
        companyDto.setCompanyTel(companyEntity.getCompanyTel());
        companyDto.setStatus(companyEntity.getStatus());
        companyDto.setPostcode(companyEntity.getPostcode());
        companyDto.setDetailAddress(companyEntity.getDetailAddress());
        companyDto.setMoney(companyEntity.getMoney());
        companyDto.setRoadAddress(companyEntity.getRoadAddress());

        return companyDto;
    }

}
