package com.example.erp.report.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class DeliveryDto {  //fetch로 받아오는용도
    private long company_id;//회사아이디
    private int location; //배송현황
}
