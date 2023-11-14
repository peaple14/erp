package com.example.erp.company.entity;

import com.example.erp.company.dto.CompanyDto;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Company")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String companyName; // 회사이름

    @Column
    private String companyVip; // 회사대표

    @Column
    private String companyTel; // 거래처 연락처

    @Column
    private String status; // 발주회사인지, 수주회사인지 (receive, send)

    @Column
    private long money ; // 받을 돈

    @Column
    private Double latitude; //회사 위도

    @Column
    private Double longitude; //회사 경도




    public static CompanyEntity toSaveEntity(CompanyDto companyDto) {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setCompanyName(companyDto.getCompanyName());
        companyEntity.setCompanyVip(companyDto.getCompanyVip());
        companyEntity.setCompanyTel(companyDto.getCompanyTel());
        companyEntity.setStatus(companyDto.getStatus());
        companyEntity.setMoney(companyDto.getMoney());

        return companyEntity;
    }

    public void update(CompanyDto companyDto) {
        this.companyName = companyDto.getCompanyName();
        this.companyVip = companyDto.getCompanyVip();
        this.companyTel = companyDto.getCompanyTel();
        this.status = companyDto.getStatus();
        this.money = companyDto.getMoney();
    }



}
