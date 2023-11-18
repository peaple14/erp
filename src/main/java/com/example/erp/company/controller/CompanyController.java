package com.example.erp.company.controller;

import com.example.erp.company.dto.CompanyDto;
import com.example.erp.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/company_list")
    public String listCompanies(Model model) {
        List<CompanyDto> companies = companyService.getAllCompanies();
        model.addAttribute("companies", companies);
        return "company/company_list";
    }

    @GetMapping("/company_add")
    public String companyAdd() {
        return "company/company_add";
    }

    @PostMapping("/company_add")
    public String companyAdd(CompanyDto companyDto) {
        System.out.println("추가되는것:" + companyDto);
        companyService.save(companyDto);
        return "redirect:/company_list";
    }

    @GetMapping("/company_memo/{id}")
    public String companyInfo(Model model, @PathVariable long id) {
        CompanyDto companyDto = companyService.findById(id);
        model.addAttribute("company", companyDto);
        return "company/company_memo";
    }

    @GetMapping("/company_edit/{id}")
    public String companyEdit(Model model, @PathVariable long id) {
        CompanyDto companyDto = companyService.findById(id);
        model.addAttribute("company", companyDto);
        return "company/company_edit";
    }

    @PostMapping("/company_edit_ok")
    public String companyEditOk(@RequestParam(name = "id") Long id,@ModelAttribute CompanyDto companyDto) {
        companyService.update(id,companyDto);
        return "redirect:/company_list";
    }
}
