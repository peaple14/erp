package com.example.erp.company.entity;

import com.example.erp.company.dto.CompanyDto;
import com.example.erp.member.entity.MemberEntity;
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
    private int moneyRecieve = 0; // 미수금 (0, 1) 받을게있으면 0

    @Column
    private long money = 0 ; // 받을 돈


    public static CompanyEntity toSaveEntity(CompanyDto companyDto) {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setCompanyName(companyDto.getCompanyName());
        companyEntity.setCompanyVip(companyDto.getCompanyVip());
        companyEntity.setCompanyTel(companyDto.getCompanyTel());
        companyEntity.setStatus(companyDto.getStatus());
        companyEntity.setMoneyRecieve(companyDto.getMoneyRecieve());
        companyEntity.setMoney(companyDto.getMoney());

        return companyEntity;
    }

    public void update(CompanyDto companyDto) {
        this.companyName = companyDto.getCompanyName();
        this.companyVip = companyDto.getCompanyVip();
        this.companyTel = companyDto.getCompanyTel();
        this.status = companyDto.getStatus();
        this.moneyRecieve = companyDto.getMoneyRecieve();
        this.money = companyDto.getMoney();
    }

}
