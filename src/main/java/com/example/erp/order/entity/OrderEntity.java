package com.example.erp.order.entity;

import com.example.erp.report.entity.QuoteEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private LocalDateTime createdAt;

    @Column
    private long quantity;

    @Column
    private String status;

    @ManyToOne
    @JoinColumn(name = "quote_id")
    private QuoteEntity quote;
}
