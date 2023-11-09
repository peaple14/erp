package com.example.erp.report.dto;



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
    private ProductEntity product; //제품명,단위단가, //거래처,대표자,연락처
    private long quantity; //수량
    private long totalprice; //총단가
    private long receivemoney; //받은돈
    private LocalDate createdat; //견적서일자
    private MemberEntity writer; // 작성자
    private int ischeck; //최종확인 되었는지 안되었는지
    private MemberEntity checkmember; //누가 최종확인했는지


    //메서드이름 다음부터 잘좀짓기
    public static QuoteDto quoteDto(QuoteEntity quoteEntity) {
        QuoteDto quoteDto = new QuoteDto();
        quoteDto.setId(quoteEntity.getId());
        quoteDto.setQuotename(quoteEntity.getQuotename());
        quoteDto.setProduct(quoteEntity.getProduct());
        quoteDto.setQuantity(quoteEntity.getQuantity());
        quoteDto.setTotalprice(quoteEntity.getTotalPrice());
        quoteDto.setReceivemoney(quoteEntity.getReceive_money());
        quoteDto.setCreatedat(quoteEntity.getCreatedAt());
        quoteDto.setIscheck(quoteEntity.getIscheck());
        quoteDto.setWriter(quoteEntity.getMember());
        return quoteDto;
    }

}
