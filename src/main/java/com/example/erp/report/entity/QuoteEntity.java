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
    private Double latitude; //회사 위도(받을장소)

    @Column
    private Double longitude; //회사 경도(받을장소)

    @Column
    private Double GoSlatitude; //출발 위도(출발장소)

    @Column
    private Double Golongitude; //출발 경도(출발장소)

    public static QuoteEntity toSaveEntity(QuoteDto quoteDto) {
        QuoteEntity quoteEntity = new QuoteEntity();
        quoteEntity.setQuotename(quoteDto.getQuotename());
        quoteEntity.setQuantity(quoteDto.getQuantity());
        quoteEntity.setTotalPrice(quoteDto.getTotalprice());
        quoteEntity.setCreatedAt(quoteDto.getCreatedat());
        quoteEntity.setProduct(quoteDto.getProduct());
        quoteEntity.setMember(quoteDto.getWriter());
        return quoteEntity;
    }


    //수정용
    public void update(QuoteDto quoteDto) {
        this.quotename = quoteDto.getQuotename();
        this.quantity = quoteDto.getQuantity();
        this.totalPrice  = quoteDto.getTotalprice();
        this.createdAt = quoteDto.getCreatedat();
        this.product = quoteDto.getProduct();
        this.location = quoteDto.getLocation();
    }

    //결제완료용
    public void check_ok(QuoteDto quoteDto) {
        this.checkmember = quoteDto.getCheckmember();
        this.location = 0;

    }

}
