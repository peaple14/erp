package com.example.erp.report.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ToString //soutp시험용도
public class QuoteDto {

    public long id; //아이디pk
    private String company;//거래처 선택시
    private String companyvip; //거래처대표자 자동등록?
    private String companytel; //거래처연락처 자동등록?
    private String product; //제품명 , 나중에는 선택후 받아오도록 수정
    private long count; //수량
    private long price; //단위단가
    private long totalprice; //총단가
    private LocalDateTime arriveTime; //입고일시 , 날짜위젯사용
    private int moneyrecieve; //미수금 받으면1 안받으면 0
    private long money; // 받은돈들
//    private String writer; // 사원관리시 넣을려했던것 작성자

}
