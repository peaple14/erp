package com.example.erp.company.controller;

import com.example.erp.company.dto.CompanyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NotReceiveController {

    @GetMapping("/notrecieve_list")
    public String listCompanies(Model model) {
//        List<CompanyDto> companies = companyService.getAllCompanies();
//        model.addAttribute("companies", companies);
        return "company/company_list";
    }
}
