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
    private int id; //실수로 int함.

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

    //배송완료 일자
    @Column
    private LocalDate endAt;

    //배달전,배달중,배달완료 = 0,1,2
    @Column
    private int location;  //현재 배송지 위치

    @Column
    private long receive_money; //받은돈들. 나중에 총단가와 비교할것.

    //견적서 최종확인자 -> 굳이 이걸 join했어야했나? 그냥 멤버id적었음 되지 않았는가?
    @ManyToOne
    @JoinColumn(name = "checkmember_id")
    private MemberEntity checkmember;

    //제품명,단위단가 , 거래처,대표자,연락처
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    //견적서 작성자
    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Column
    private String gopostcode; //출발우편번호

    @Column
    private String godetailAddress; //출발상세주소

    @Column
    private String goroadAddress; //출발도로명주소

    @Column
    private String endpostcode; //받을우편번호

    @Column
    private String enddetailAddress; //받을상세주소

    @Column
    private String endroadAddress; //받을도로명주소

    public static QuoteEntity toSaveEntity(QuoteDto quoteDto) {
        QuoteEntity quoteEntity = new QuoteEntity();
        quoteEntity.setQuotename(quoteDto.getQuotename());
        quoteEntity.setQuantity(quoteDto.getQuantity());
        quoteEntity.setTotalPrice(quoteDto.getTotalprice());
        quoteEntity.setCreatedAt(quoteDto.getCreatedat());
        quoteEntity.setProduct(quoteDto.getProduct());
        quoteEntity.setMember(quoteDto.getWriter());
        quoteEntity.setGopostcode(quoteDto.getGopostcode());
        quoteEntity.setGoroadAddress(quoteDto.getGoroadAddress());
        quoteEntity.setEndAt(quoteDto.getEndat());
        quoteEntity.setGodetailAddress(quoteDto.getGodetailAddress());
        quoteEntity.setEndpostcode(quoteDto.getEndpostcode());
        quoteEntity.setEndroadAddress(quoteDto.getEndroadAddress());
        quoteEntity.setEnddetailAddress(quoteDto.getEnddetailAddress());
        return quoteEntity;
    }


    //수정용
    public void update(QuoteDto quoteDto) {
        this.quotename = quoteDto.getQuotename();
        this.quantity = quoteDto.getQuantity();
        this.totalPrice  = quoteDto.getTotalprice();
        this.createdAt = quoteDto.getCreatedat();
        this.product = quoteDto.getProduct();
        this.member=quoteDto.getWriter();
        this.location = quoteDto.getLocation();
        this.gopostcode=quoteDto.getGopostcode();
        this.goroadAddress=quoteDto.getGoroadAddress();
        this.godetailAddress=quoteDto.getGodetailAddress();
        this.endpostcode=quoteDto.getEndpostcode();
        this.endroadAddress=quoteDto.getEndroadAddress();
        this.enddetailAddress=quoteDto.getEnddetailAddress();
    }

    //결제완료용
    public void check_ok(QuoteDto quoteDto) {
        this.checkmember = quoteDto.getCheckmember();
        this.location = 0;

    }

}
