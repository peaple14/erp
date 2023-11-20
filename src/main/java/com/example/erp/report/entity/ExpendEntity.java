package com.example.erp.report.entity;


import com.example.erp.member.entity.MemberEntity;
import com.example.erp.product.entity.ProductEntity;
import com.example.erp.report.dto.ExpendDto;
import com.example.erp.report.dto.QuoteDto;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "Expend_table")
public class ExpendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //실수로 int함.

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

    //배송완료 일자
    @Column
    private LocalDate endAt;

    //배달전,배달중,배달완료 = 0,1,2
    @Column
    private int location;  //현재 배송지 위치

    @Column
    private long receive_money; //받은돈들. 나중에 총단가와 비교할것.

    //지출결의서 최종확인자
    @ManyToOne
    @JoinColumn(name = "checkmember_id")
    private MemberEntity checkmember;

    //제품명,단위단가 , 거래처,대표자,연락처
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    //지출결의서 작성자
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

    @Column
    private String uploadFileName; //원래첨부파일이름
    @Column
    private String storeFileName; //저장된 첨부파일 이름

    public static ExpendEntity toSaveEntity(ExpendDto expendDto) {
        ExpendEntity expendEntity = new ExpendEntity();
        expendEntity.setExpendname(expendDto.getExpendname());
        expendEntity.setQuantity(expendDto.getQuantity());
        expendEntity.setTotalPrice(expendDto.getTotalprice());
        expendEntity.setCreatedAt(expendDto.getCreatedat());
        expendEntity.setProduct(expendDto.getProduct());
        expendEntity.setMember(expendDto.getWriter());
        expendEntity.setGopostcode(expendDto.getGopostcode());
        expendEntity.setGoroadAddress(expendDto.getGoroadAddress());
        expendEntity.setEndAt(expendDto.getEndat());
        expendEntity.setGodetailAddress(expendDto.getGodetailAddress());
        expendEntity.setEndpostcode(expendDto.getEndpostcode());
        expendEntity.setEndroadAddress(expendDto.getEndroadAddress());
        expendEntity.setEnddetailAddress(expendDto.getEnddetailAddress());
        expendEntity.setUploadFileName(expendDto.getUploadFileName());
        expendEntity.setStoreFileName(expendDto.getStoreFileName());

        return expendEntity;
    }


    //수정용
    public void update(ExpendDto expendDto) {
        this.expendname = expendDto.getExpendname();
        this.quantity = expendDto.getQuantity();
        this.totalPrice  = expendDto.getTotalprice();
        this.createdAt = expendDto.getCreatedat();
        this.product = expendDto.getProduct();
        this.member=expendDto.getWriter();
        this.location = expendDto.getLocation();
        this.gopostcode=expendDto.getGopostcode();
        this.goroadAddress=expendDto.getGoroadAddress();
        this.godetailAddress=expendDto.getGodetailAddress();
        this.endAt=expendDto.getEndat();
        this.endpostcode=expendDto.getEndpostcode();
        this.endroadAddress=expendDto.getEndroadAddress();
        this.enddetailAddress=expendDto.getEnddetailAddress();
        this.uploadFileName = expendDto.getUploadFileName();
        this.storeFileName = expendDto.getStoreFileName();
    }

    //결제완료용
    public void check_ok(ExpendDto expendDto) {
        this.checkmember = expendDto.getCheckmember();
        this.location = 0;

    }

}
