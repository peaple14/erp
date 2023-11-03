package com.example.erp.report.dto;


import com.example.erp.company.dto.CompanyDto;
import com.example.erp.company.entity.CompanyEntity;
import com.example.erp.member.entity.MemberEntity;
import com.example.erp.product.entity.ProductEntity;
import com.example.erp.report.entity.ExpendEntity;
import com.example.erp.report.entity.QuoteEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ToString //soutp시험용도
public class ExpendDto {

    public long id; //아이디pk
    private String expendname; //지출결의서이름
    private CompanyEntity company;//거래처,대표자,연락처
    private ProductEntity product; //제품명,단위단가
    private long quantity; //수량
    private long totalprice; //총단가
    private LocalDate writetime; //일자선택
    private MemberEntity writer; // 작성자
    private String check; //최종확인 되었는지 안되었는지


    public static ExpendDto expendDto(ExpendEntity expendEntity) {
        ExpendDto expendDto = new ExpendDto();
        expendDto.setId(expendEntity.getId());
        expendDto.setExpendname(expendEntity.getExpendname());
        expendDto.setCompany(expendEntity.getCompany());
        expendDto.setProduct(expendEntity.getProduct());
        expendDto.setQuantity(expendEntity.getQuantity());
        expendDto.setTotalprice(expendEntity.getTotalPrice());
        expendDto.setWritetime(expendEntity.getCreatedAt());
        expendDto.setWriter(expendEntity.getMember());
        return expendDto;
    }

}
