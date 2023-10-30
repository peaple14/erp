package com.example.erp.report.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class QuoteController {



    //처음 견적서왔을때 리스트
    @GetMapping("/Quote")
    public String quote(){
        return "1";
    }



}
