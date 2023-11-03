package com.example.erp.product.entity;

import com.example.erp.company.entity.CompanyEntity;
import com.example.erp.product.dto.ProductDto;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.YearMonth;

@Entity
@Data
@Table(name = "Product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //제품이름
    @Column
    private String productName;
    //제품코드
    @Column
    private String productCode;
    //재고량
    @Column
    private long count;
    //가격
    @Column
    private long price;
    //제조일자
    @Column
    private YearMonth makeDay;
    //만든회사
    @ManyToOne
    @JoinColumn
    private CompanyEntity company;



    public static ProductEntity toSaveEntity(ProductDto productDto) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName(productDto.getProductname());
        productEntity.setProductCode(productDto.getProductcode());
        productEntity.setCount(0);
        productEntity.setPrice(productDto.getPrice());
        productEntity.setCompany(productDto.getMakecompany());
        productEntity.setMakeDay(productDto.getMakeday());

        return productEntity;
    }

    public void update(ProductDto productDto) {
        this.productName = productDto.getProductname();
        this.count = productDto.getCount();
        this.price = productDto.getPrice();
        this.company = productDto.getMakecompany();
        this.makeDay = productDto.getMakeday();
    }


}
