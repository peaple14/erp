package com.example.erp.report.entity;

import com.example.erp.company.dto.CompanyDto;
import com.example.erp.company.entity.CompanyEntity;
import com.example.erp.member.entity.MemberEntity;
import com.example.erp.product.entity.ProductEntity;
import com.example.erp.report.dto.ExpendDto;
import com.example.erp.report.dto.QuoteDto;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "Expend")
public class ExpendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String expendname;

    //수량
    @Column
    private long quantity;

    //총 단가
    @Column
    private long totalPrice;

    //지출결의서 일자선택
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

    //지출결의서 작성자
    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    public static ExpendEntity toSaveEntity(ExpendDto expendDto) {
        ExpendEntity expendEntity = new ExpendEntity();
        expendEntity.setExpendname(expendDto.getExpendname());
        expendEntity.setQuantity(expendDto.getQuantity());
        expendEntity.setTotalPrice(expendDto.getTotalprice());
        expendEntity.setCreatedAt(expendDto.getWritetime());
        expendEntity.setCompany(expendDto.getCompany());
        expendEntity.setMember(expendDto.getWriter());
        return expendEntity;
    }


    //수정용
    public void update(ExpendDto expendDto) {
        this.expendname = expendDto.getExpendname();
        this.quantity = expendDto.getQuantity();
        this.totalPrice = expendDto.getTotalprice();
        this.createdAt = expendDto.getWritetime();
        this.company = expendDto.getCompany();
        this.member = expendDto.getWriter();
    }

}
