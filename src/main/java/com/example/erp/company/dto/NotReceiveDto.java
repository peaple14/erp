package com.example.erp.company.dto;

import com.example.erp.company.entity.CompanyEntity;
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

    private int id;
    private long receiveMoney;
    private QuoteEntity quote;

    public static NotReceiveDto notReceiveDto(NotReceiveEntity notReceiveEntity) {
        NotReceiveDto notReceiveDto = new NotReceiveDto();
        notReceiveDto.setId(notReceiveEntity.getId());
        notReceiveDto.setReceiveMoney(notReceiveEntity.getReceiveMoney());
        notReceiveDto.setQuote(notReceiveEntity.getQuote());
        return notReceiveDto;
    }


}
