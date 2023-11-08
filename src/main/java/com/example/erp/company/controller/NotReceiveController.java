package com.example.erp.company.controller;

import com.example.erp.company.dto.NotReceiveDto;
import com.example.erp.company.service.NotReceiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Controller
@RequiredArgsConstructor
public class NotReceiveController {

    private final NotReceiveService notReceiveService;

    @GetMapping("/recieve_money") //받은돈 들어왔을때
    public String listCompanies(Model model) {
        long company = 1;//받은 회사 아이디
        long rmoney = 1000; // 받은돈
        notReceiveService.setlist(2L, 1000L);
        return "company/company_list";
    }

    //리스트 띄워주기
    @GetMapping("/not_receive_list")
    public String listNotReceive(Model model) {
        model.addAttribute("notReceiveList", notReceiveService.getallNReceive());
        return "company/not_receive_list";
    }
}

