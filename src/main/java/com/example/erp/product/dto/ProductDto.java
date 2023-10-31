package com.example.erp.product.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ToString //soutp시험용도
public class ProductDto {

    public long id; //아이디pk
    private String productname;//제품명
    private String productcode;//제품코드
    private String count; //재고량
    private long price; //제품가격
    private String makecompany; //제조업체
    private LocalDateTime makeday; //제조년도
}
