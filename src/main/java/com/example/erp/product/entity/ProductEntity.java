package com.example.erp.product.entity;

import com.example.erp.company.entity.CompanyEntity;

import javax.persistence.*;

@Entity
@Table(name = "Product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "product_name")
    private String productName;

    private long price;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;
}
