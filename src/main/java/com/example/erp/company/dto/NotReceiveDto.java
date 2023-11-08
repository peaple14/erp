package com.example.erp.company.dto;

import com.example.erp.company.entity.NotReceiveEntity;
import com.example.erp.report.entity.QuoteEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class NotReceiveDto {
    //list에서 보여주는용도.

    private long id;
    private long receiveMoney;//받은돈
    private String company; //회사이름
    private Long company_id; //회사 아이디값
    private long not_receive_money;//미수금

    public static NotReceiveDto tonotReceiveDto(NotReceiveEntity notReceiveEntity) {
        NotReceiveDto notReceiveDto = new NotReceiveDto();
        notReceiveDto.setId(notReceiveEntity.getId());
        notReceiveDto.setCompany(notReceiveEntity.getQuote().getProduct().getCompany().getCompanyName());
        notReceiveDto.setReceiveMoney(notReceiveEntity.getQuote().getReceive_money());
        return notReceiveDto;
    }


}
