package com.example.erp.report.dto;


import com.example.erp.company.dto.CompanyDto;
import com.example.erp.company.entity.CompanyEntity;
import com.example.erp.member.entity.MemberEntity;
import com.example.erp.product.entity.ProductEntity;
import com.example.erp.report.entity.QuoteEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ToString //soutp시험용도
public class QuoteDto {

    public long id; //아이디pk
    private String quotename; //견적서이름
    private CompanyEntity company;//거래처,대표자,연락처
    private ProductEntity product; //제품명,단위단가
    private long quantity; //수량
    private long totalprice; //총단가
    private LocalDate writetime; //일자선택
    private MemberEntity writer; // 작성자

    public static QuoteDto quoteDto(QuoteEntity quoteEntity) {
        QuoteDto quoteDto = new QuoteDto();
        quoteDto.setId(quoteEntity.getId());
        quoteDto.setQuotename(quoteEntity.getQuotename());
        quoteDto.setCompany(quoteEntity.getCompany());
        quoteDto.setProduct(quoteEntity.getProduct());
        quoteDto.setQuantity(quoteEntity.getQuantity());
        quoteDto.setTotalprice(quoteEntity.getTotalAmount());
        quoteDto.setWritetime(quoteEntity.getCreatedAt());
        quoteDto.setWriter(quoteEntity.getMember());
        return quoteDto;
    }

}
