package com.example.erp.company.dto;

import com.example.erp.company.entity.NotReceiveEntity;
import com.example.erp.report.entity.QuoteEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@ToString
public class NotReceiveDto {
    //list에서 보여주는용도.

    private long id;
    private long receiveMoney;//받은돈
    private String company; //회사이름
    private long Company_id; //회사아이디
    private String product; //제품이름
    private Long totalprice; //총가격
    private long not_receive_money;//미수금
    private LocalDate createdat;//견적서 날짜

    public static NotReceiveDto tonotReceiveDto(NotReceiveEntity notReceiveEntity) {
        NotReceiveDto notReceiveDto = new NotReceiveDto();
        notReceiveDto.setId(notReceiveEntity.getId());
        notReceiveDto.setCompany(notReceiveEntity.getQuote().getProduct().getCompany().getCompanyName());
        notReceiveDto.setCompany_id(notReceiveEntity.getQuote().getProduct().getCompany().getId());
        notReceiveDto.setProduct(notReceiveEntity.getQuote().getProduct().getProductName());
        notReceiveDto.setTotalprice(notReceiveEntity.getQuote().getTotalPrice());
        notReceiveDto.setReceiveMoney(notReceiveEntity.getQuote().getReceive_money());
        notReceiveDto.setCreatedat(notReceiveEntity.getQuote().getCreatedAt());
        return notReceiveDto;
    }


}
