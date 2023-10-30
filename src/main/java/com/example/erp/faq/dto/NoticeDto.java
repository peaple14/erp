package com.example.erp.faq.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class NoticeDto {
    private Long id;
    private String noticeTitle;
    private String noticeMemo;
}
