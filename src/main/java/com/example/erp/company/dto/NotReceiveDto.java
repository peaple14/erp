package com.example.erp.company.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class NotReceiveDto {
    //list에서 보여주는용도.

    private long id;
    private String companyName;
    private String status;
    private int money; //받을돈


}
