package com.example.erp.report.entity;

import com.example.erp.company.dto.CompanyDto;
import com.example.erp.company.entity.CompanyEntity;
import com.example.erp.member.entity.MemberEntity;
import com.example.erp.product.entity.ProductEntity;
import com.example.erp.report.dto.QuoteDto;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "Quote")
public class QuoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String quotename;

    //수량
    @Column
    private long quantity;

    //총 단가
    @Column
    private long totalAmount;

    //견적서 일자선택
    @Column
    private LocalDate createdAt;

    //거래처,대표자,연락처
    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    //제품명,단위단가
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    //견적서 작성자
    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    public static QuoteEntity toSaveEntity(QuoteDto quoteDto) {
        QuoteEntity quoteEntity = new QuoteEntity();
        quoteEntity.setQuotename(quoteDto.getQuotename());
        quoteEntity.setQuantity(quoteDto.getQuantity());
        quoteEntity.setTotalAmount(quoteDto.getTotalprice());
        quoteEntity.setCreatedAt(quoteDto.getWritetime());
        quoteEntity.setCompany(quoteDto.getCompany());
        quoteEntity.setMember(quoteDto.getWriter());
        return quoteEntity;
    }

    public void update(QuoteDto quoteDto) {
        this.quotename = quoteDto.getQuotename();
        this.quantity = quoteDto.getQuantity();
        this.totalAmount = quoteDto.getTotalprice();
        this.createdAt = quoteDto.getWritetime();
        this.company = quoteDto.getCompany();
        this.member = quoteDto.getWriter();
    }

}
