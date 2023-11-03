package com.example.erp.report.controller;

import com.example.erp.company.entity.CompanyEntity;
import com.example.erp.company.service.CompanyService;
import com.example.erp.member.entity.MemberEntity;
import com.example.erp.product.entity.ProductEntity;
import com.example.erp.report.dto.QuoteDto;
import com.example.erp.report.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;

    //리스트띄우기
    @GetMapping("/quote_list")
    public String listQuotes(Model model) {
        List<QuoteDto> quotes = quoteService.getAllQuotes();
        model.addAttribute("quotes", quotes);
        System.out.println("quote 리스트띄우기:" + quotes);
        return "report/quote/quote_list";
    }

    //견적서추가
    @GetMapping("/quote_add")
    public String quoteAdd(Model model) {
        List<ProductEntity> products = quoteService.getAllProducts();

        model.addAttribute("quoteDto", new QuoteDto());
        model.addAttribute("products", products);
        return "report/quote/quote_add";
    }

    @PostMapping("/quote_add")
    public String quoteAdd(@ModelAttribute QuoteDto quoteDto, HttpSession session) {
        // 추가: HTML 폼에서 입력한 데이터를 로그로 출력
        System.out.println("견적서 추가 폼 제출 데이터: " + quoteDto);
        System.out.println("로그인 세션 정보: " + session.getAttribute("loginId"));

        quoteDto.setWriter(quoteService.getMember((String) session.getAttribute("loginId")));
        quoteService.save(quoteDto);

        return "redirect:/quote_list";
    }

    @GetMapping("/quote_memo/{id}")
    public String quoteInfo(Model model, @PathVariable int id) {
        QuoteDto quoteDto = quoteService.findById(id);
        model.addAttribute("quote", quoteDto);
        System.out.println("자세히보기에 들어오는 데이터:" + quoteDto);
        return "report/quote/quote_memo";
    }

    @GetMapping("/quote_edit/{id}")
    public String quoteEdit(Model model, @PathVariable int id) {
        QuoteDto quoteDto = quoteService.findById(id);
        model.addAttribute("quoteDto", quoteDto);
        System.out.println("수정데이터값들:" + quoteDto);
        return "report/quote/quote_edit";
    }

    @PostMapping("/quote_edit_ok")
    public String quoteEditOk(@RequestParam(name = "id") int id, @ModelAttribute QuoteDto quoteDto) {
        quoteService.update(id, quoteDto);
        return "redirect:/quote_list";
    }

    @GetMapping ("/quote_check_ok/{id}")
    public String check_ok(@PathVariable int id, HttpSession session,@ModelAttribute QuoteDto quoteDto){
        System.out.println("결제완료됨." + id);
        System.out.println("로그인 세션 정보: " + session.getAttribute("loginId"));
        quoteDto.setCheckmember(quoteService.getMember((String) session.getAttribute("loginId")));
        quoteDto.setIscheck(1);
        quoteService.check_ok(id,quoteDto);
        return "redirect:/quote_list";
    }
}
