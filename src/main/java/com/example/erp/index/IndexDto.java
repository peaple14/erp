package com.example.erp.index;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@Data
@NoArgsConstructor
public class IndexDto {

    private YearMonth saleday; //팔린날짜
    private String companyname;//회사명
    private String productname;//제품명
    private long count ; //갯수
    private long price; //제품가격


}
