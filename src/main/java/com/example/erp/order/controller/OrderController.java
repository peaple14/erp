package com.example.erp.order.controller;

import com.example.erp.report.dto.ExpendDto;
import com.example.erp.report.dto.QuoteDto;
import com.example.erp.report.service.ExpendService;
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
    private final ExpendService expendService;

    //진행중인 주문(견적서)
    @GetMapping("/go_order_list")
    public String goorder(Model model) {
        List<QuoteDto> quoteDto = quoteService.getgoQuotes(1);
        model.addAttribute("quotes", quoteDto);
        return "order/go_order_list";
    }

    //견적서끝난 주문
    @GetMapping("/quote_end_order_list")
    public String endorder1(Model model) {
        List<QuoteDto> quoteDto = quoteService.getgoQuotes(2);
        model.addAttribute("quotes", quoteDto);
        return "order/quote_end_order_list";
    }

    //지출결의서
    @GetMapping("/expend_end_order_list")
    public String endorder2(Model model) {
        List<ExpendDto> expendDto = expendService.getgoexpends(2);
        model.addAttribute("expends", expendDto);
        return "order/expend_end_order_list";
    }
}
