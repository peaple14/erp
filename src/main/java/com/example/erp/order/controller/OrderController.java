package com.example.erp.order.controller;

import com.example.erp.report.dto.QuoteDto;
import com.example.erp.report.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final QuoteService quoteService;

    //진행중인 주문
    @GetMapping("/goorder_list")
    public String listExpends(Model model) {
        List<QuoteDto> quoteDto = quoteService.getgoQuotes();
        System.out.println("이게되나" + quoteDto);
        model.addAttribute("quotes", quoteDto);
        return "order/go_order_list";
    }


}
