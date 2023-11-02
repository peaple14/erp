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
    private long id;

    @Column
    private String productName;

    @Column
    private String productCode;

    @Column
    private String count;

    @Column
    private long price;

    @Column
    private YearMonth makeDay;

    @ManyToOne
    @JoinColumn
    private CompanyEntity company;



    public static ProductEntity toSaveEntity(ProductDto productDto) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName(productDto.getProductname());
        productEntity.setProductCode(productDto.getProductcode());
        productEntity.setCount(productDto.getCount());
        productEntity.setPrice(productDto.getPrice());
        productEntity.setCompany(productDto.getMakecompany());
        productEntity.setMakeDay(productDto.getMakeday());

        return productEntity;
    }

    public void update(ProductDto productDto) {
        this.productName = productDto.getProductname();
        this.productCode = productDto.getProductcode();
        this.count = productDto.getCount();
        this.price = productDto.getPrice();
        this.company = productDto.getMakecompany();
        this.makeDay = productDto.getMakeday();
    }


}
