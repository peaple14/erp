package com.example.erp.report.entity;

import com.example.erp.member.entity.MemberEntity;
import com.example.erp.product.entity.ProductEntity;
import com.example.erp.report.dto.ExpendDto;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "Expend_table")
public class ExpendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String expendname;

    @Column
    private long quantity;

    @Column
    private long totalPrice;

    @Column
    private LocalDate createdAt;

    @Column
    private int location;

    @ManyToOne
    @JoinColumn(name = "checkmember_id")
    private MemberEntity checkmember;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    public static ExpendEntity toSaveEntity(ExpendDto expendDto) {
        ExpendEntity expendEntity = new ExpendEntity();
        expendEntity.setExpendname(expendDto.getExpendname());
        expendEntity.setQuantity(expendDto.getQuantity());
        expendEntity.setTotalPrice(expendDto.getTotalprice());
        expendEntity.setCreatedAt(expendDto.getCreatedat());
        expendEntity.setProduct(expendDto.getProduct());
        expendEntity.setMember(expendDto.getWriter());
        return expendEntity;
    }

    public void update(ExpendDto expendDto) {
        this.expendname = expendDto.getExpendname();
        this.quantity = expendDto.getQuantity();
        this.totalPrice = expendDto.getTotalprice();
        this.createdAt = expendDto.getCreatedat();
        this.product = expendDto.getProduct();
//        this.location = expendDto.getIscheck();
    }

    public void check_ok(ExpendDto expendDto) {
        this.checkmember = expendDto.getCheckmember();
        this.location = 0;
    }
}