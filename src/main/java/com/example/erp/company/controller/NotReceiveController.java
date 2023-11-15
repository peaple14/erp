package com.example.erp.company.controller;

import com.example.erp.company.service.NotReceiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class NotReceiveController {

    private final NotReceiveService notReceiveService;

    //리스트 띄워주기
    @GetMapping("/not_receive_list")
    public String listNotReceive(Model model) {
        model.addAttribute("notReceiveList", notReceiveService.getallNReceive());
        System.out.println(model);
        return "company/not_receive_list";
    }
}

