package com.example.erp.company.entity;

import javax.persistence.*;

@Entity
@Table(name = "Company")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_vip")
    private String companyVip;

    @Column(name = "company_tel")
    private String companyTel;

    private String status;

    @Column(name = "moneyrecieve")
    private int moneyRecieve;

    private long money;

    // 생성자, 게터, 세터, 기타 필요한 메서드
}
