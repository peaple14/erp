package com.example.erp.product.entity;

import com.example.erp.company.entity.CompanyEntity;

import javax.persistence.*;

@Entity
@Table(name = "Product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String productCode;

    @Column
    private String productName;

    @Column
    private long price;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;
}
