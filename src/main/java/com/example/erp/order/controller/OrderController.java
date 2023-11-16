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
    @GetMapping("/go_order_list")
    public String goorder(Model model) {
        List<QuoteDto> quoteDto = quoteService.getgoQuotes(1);
        model.addAttribute("quotes", quoteDto);
        return "order/go_order_list";
    }

    //끝난 주문
    @GetMapping("/end_order_list")
    public String endorder(Model model) {
        List<QuoteDto> quoteDto = quoteService.getgoQuotes(2);
        model.addAttribute("quotes", quoteDto);
        return "order/go_order_list";
    }

}
