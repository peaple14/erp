package com.example.erp.report.entity;

import com.example.erp.company.entity.CompanyEntity;
import com.example.erp.member.entity.MemberEntity;
import com.example.erp.product.entity.ProductEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Quote")
public class QuoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private long quantity;

    @Column(name = "unit_price")
    private long unitPrice;

    @Column(name = "total_amount")
    private long totalAmount;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;
}
