package com.example.erp.order.entity;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "expense_table")
public class ExpenseReportentity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private LocalDateTime createdAt;

    @Column
    private String expenseItem;

    @Column
    private long expenseAmount;

    @Column
    private String status;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
}
