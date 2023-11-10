package com.example.erp.product.dto;


import com.example.erp.company.entity.CompanyEntity;
import com.example.erp.product.entity.ProductEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.YearMonth;

@Data
@NoArgsConstructor
@ToString //soutp시험용도
public class ProductDto {

    public long id; //아이디pk
    private String productname;//제품명
    private String productcode;//제품코드
    private long count ; //재고량
    private long price; //제품가격
    private CompanyEntity makecompany; //제조업체
    private YearMonth makeday; //제조년도

    public static ProductDto toProductDto(ProductEntity productEntity) {
        ProductDto productDto = new ProductDto();
        productDto.setId(productEntity.getId());
        productDto.setProductname(productEntity.getProductName());
        productDto.setProductcode(productEntity.getProductCode());
        productDto.setCount(productEntity.getCount());
        productDto.setPrice(productEntity.getPrice());
        productDto.setMakecompany(productEntity.getCompany());
        productDto.setMakeday(productEntity.getMakeDay());
        return productDto;
    }
}
