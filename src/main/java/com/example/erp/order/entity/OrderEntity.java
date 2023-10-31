package com.example.erp.order.entity;

import com.example.erp.report.entity.QuoteEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Order")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private long quantity;

    private String status;

    @ManyToOne
    @JoinColumn(name = "quote_id")
    private QuoteEntity quote;
}
