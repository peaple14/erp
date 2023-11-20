package com.example.erp.report.dto;


import com.example.erp.member.entity.MemberEntity;
import com.example.erp.product.entity.ProductEntity;
import com.example.erp.report.entity.ExpendEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@ToString // soutp 시험용도
public class ExpendDto {

    public long id; // 아이디 pk
    private String expendname; // 지출결의서 이름
    private ProductEntity product; // 제품명, 단위 단가, 거래처, 대표자, 연락처
    private long quantity; // 수량
    private long totalprice; // 총 단가
    private LocalDate createdat; // 지출결의서 일자
    private LocalDate endat; //배송완료일자
    private MemberEntity writer; // 작성자
    private MemberEntity checkmember; // 누가 최종 확인했는지
    private String gopostcode; //출발우편번호
    private String godetailAddress; //출발상세주소
    private String goroadAddress; //출발도로명주소
    private String endpostcode; //받을우편번호
    private String enddetailAddress; //받을상세주소
    private String endroadAddress; //받을도로명주소

    public static ExpendDto expendDto(ExpendEntity expendEntity) {
        ExpendDto expendDto = new ExpendDto();
        expendDto.setId(expendEntity.getId());
        expendDto.setExpendname(expendEntity.getExpendname());
        expendDto.setProduct(expendEntity.getProduct());
        expendDto.setEndat(expendEntity.getEndAt());
        expendDto.setQuantity(expendEntity.getQuantity());
        expendDto.setTotalprice(expendEntity.getTotalPrice());
        expendDto.setCreatedat(expendEntity.getCreatedAt());
        expendDto.setCheckmember(expendEntity.getCheckmember());
        expendDto.setWriter(expendEntity.getMember());
        return expendDto;
    }
}