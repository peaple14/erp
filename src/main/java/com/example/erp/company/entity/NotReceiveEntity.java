package com.example.erp.company.entity;

import javax.persistence.*;


import com.example.erp.report.entity.QuoteEntity;
import lombok.Data;

@Entity
@Data
@Table(name = "not_receive")
public class NotReceiveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @ManyToOne
    @JoinColumn(name = "quote_id")
    private QuoteEntity quote; //미수금된 것들 여기서 처리 + 안받은 돈들


    public static NotReceiveEntity toSaveEntity(QuoteEntity quoteEntity) {
        NotReceiveEntity notReceiveEntity = new NotReceiveEntity();
        notReceiveEntity.setId(quoteEntity.getId());
        notReceiveEntity.setQuote(quoteEntity);
        return notReceiveEntity;
    }

}
