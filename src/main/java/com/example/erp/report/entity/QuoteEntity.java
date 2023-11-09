package com.example.erp.report.entity;


import com.example.erp.member.entity.MemberEntity;
import com.example.erp.product.entity.ProductEntity;
import com.example.erp.report.dto.QuoteDto;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "Quote_table")
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
    private long totalPrice;

    //견적서 일자선택
    @Column
    private LocalDate createdAt;

    //최종 확인되었는지 안되었는지
    @Column
    private int ischeck ;  //다음부터는 true/false 방식쓰기 ->나중에 삭제하기. memberEntity여부만 보면될듯함.

    @Column
    private long receive_money; //받은돈들. 나중에 총단가와 비교할것.

    //견적서 최종확인자 -> 굳이 이걸 join했어야했나? 그냥 멤버id적었음 되지 않았는가?
    @ManyToOne
    @JoinColumn(name = "checkmember_id")
    private MemberEntity checkmember;

//    //거래처,대표자,연락처 ,중복되서 제거
//    @ManyToOne
//    @JoinColumn(name = "company_id")
//    private CompanyEntity company;

    //제품명,단위단가 , 거래처,대표자,연락처
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    //견적서 작성자 -> 굳이 이걸 join했어야했나? 그냥 멤버id적었음 되지 않았는가?
    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    public static QuoteEntity toSaveEntity(QuoteDto quoteDto) {
        QuoteEntity quoteEntity = new QuoteEntity();
        quoteEntity.setQuotename(quoteDto.getQuotename());
        quoteEntity.setQuantity(quoteDto.getQuantity());
        quoteEntity.setTotalPrice(quoteDto.getTotalprice());
        quoteEntity.setCreatedAt(quoteDto.getCreatedat());
        quoteEntity.setProduct(quoteDto.getProduct());
        quoteEntity.setMember(quoteDto.getWriter());
        quoteEntity.setIscheck(quoteDto.getIscheck());
        return quoteEntity;
    }


    //수정용
    public void update(QuoteDto quoteDto) {
        this.quotename = quoteDto.getQuotename();
        this.quantity = quoteDto.getQuantity();
        this.totalPrice  = quoteDto.getTotalprice();
        this.createdAt = quoteDto.getCreatedat();
        this.product = quoteDto.getProduct();
        this.ischeck = quoteDto.getIscheck();
    }

    //결제완료용
    public void check_ok(QuoteDto quoteDto) {
        this.checkmember = quoteDto.getCheckmember();
        this.ischeck = 1;

    }

}
