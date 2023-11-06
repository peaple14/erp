package com.example.erp.company.controller;

import com.example.erp.company.dto.CompanyDto;
import com.example.erp.company.service.CompanyService;
import com.example.erp.company.service.NotReceiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NotReceiveController {

    private final NotReceiveService notReceiveService;

    @GetMapping("/recieve_money") //받은돈 들어왔을때
    public String listCompanies(Model model) {
        long company = 1;//받은 회사 아이디
        long rmoney = 1000; // 받은돈

        notReceiveService.setlist(3L, 1000L);

        
        return "company/company_list";
    }
}
