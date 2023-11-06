package com.example.erp.company.entity;

import javax.persistence.*;

import com.example.erp.company.dto.NotReceiveDto;
import com.example.erp.report.entity.QuoteEntity;
import lombok.Data;

@Entity
@Data
@Table(name = "not_receive")
public class NotReceiveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private long receiveMoney; //받은 돈들

    @ManyToOne
    @JoinColumn(name = "quote_id")
    private QuoteEntity quote; //미수금된 것들 여기서 처리 + 안받은 돈들


    public static NotReceiveEntity toSaveEntity(NotReceiveDto notReceiveDto) {
        NotReceiveEntity notReceiveEntity = new NotReceiveEntity();
        notReceiveEntity.setId(notReceiveDto.getId());
        notReceiveEntity.setReceiveMoney(notReceiveDto.getReceiveMoney());
        notReceiveEntity.setQuote(notReceiveDto.getQuote());
        return notReceiveEntity;
    }

    public NotReceiveEntity(long receiveMoney, QuoteEntity quote) {
        this.receiveMoney = receiveMoney;
        this.quote = quote;
    }


}
