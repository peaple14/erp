package com.example.erp.report.controller;

import com.example.erp.member.service.NotificationService;
import com.example.erp.product.entity.ProductEntity;
import com.example.erp.report.dto.DeliveryDto;
import com.example.erp.report.dto.QuoteDto;
import com.example.erp.report.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;
    private final NotificationService notificationService;
    //리스트띄우기
    @GetMapping("/quote_list")
    public String listQuotes(Model model) {
        List<QuoteDto> quotes = quoteService.getAllQuotes();
        model.addAttribute("quotes", quotes);
        return "report/quote/quote_list";
    }

    //견적서추가
    @GetMapping("/quote_add")
    public String quoteAdd(Model model) {
        List<ProductEntity> products = quoteService.getAllProducts();
        System.out.println(products);
        model.addAttribute("quoteDto", new QuoteDto());
        model.addAttribute("products", products);
        return "report/quote/quote_add";
    }

    @PostMapping("/quote_add")
    public String quoteAdd(@ModelAttribute QuoteDto quoteDto, HttpSession session) {
        quoteDto.setLocation(0);//
        quoteDto.setWriter(quoteService.getMember((String) session.getAttribute("loginId")));
        notificationService.sendToClient(1L, "견적서가 추가되었습니다."); //로그인된 모든 admin에게 알람.
        quoteService.save(quoteDto);
        return "redirect:/quote_list";
    }



    @GetMapping("/quote_memo/{id}")
    public String quoteInfo(Model model, @PathVariable int id) {
        QuoteDto quoteDto = quoteService.findById(id);
        model.addAttribute("quote", quoteDto);
//        System.out.println("자세히보기에 들어오는 데이터:" + quoteDto);
        return "report/quote/quote_memo";
    }

    @GetMapping("/quote_edit/{id}")
    public String quoteEdit(Model model, @PathVariable int id) {
        List<ProductEntity> products = quoteService.getAllProducts();
        model.addAttribute("products", products);
        QuoteDto quoteDto = quoteService.findById(id);
        model.addAttribute("quoteDto", quoteDto);
        return "report/quote/quote_edit";
    }

    @PostMapping("/quote_edit_ok")
    public String quoteEditOk(@RequestParam(name = "id") int id, @ModelAttribute QuoteDto quoteDto) {
        quoteService.update(id, quoteDto);
        return "redirect:/quote_list";
    }


    //결제완료시 미수금,수주거래 변동.
    @GetMapping ("/quote_check_ok/{id}")
    public String check_ok(@PathVariable int id, HttpSession session,@ModelAttribute QuoteDto quoteDto){

        //권한이 admin인사람만 하도록.
        quoteDto.setCheckmember(quoteService.getMember((String) session.getAttribute("loginId")));
        System.out.println("권한:" + quoteDto.getCheckmember().getUserauthority());

        if (!"ADMIN".equals(quoteDto.getCheckmember().getUserauthority())) {
            System.out.println("권한이 안됨");
            return "redirect:/quote_list";
        }
        System.out.println("권한이됨");

        //미수금 처리들 하기
        quoteService.mesugm(id, quoteDto);
        return "redirect:/quote_list";
    }

    //배송현재상황
    @PostMapping("/delievery")
    public ResponseEntity<String> receivePayment(@RequestBody DeliveryDto dto) {
        long companyId = dto.getCompany_id();
        int location = dto.getLocation();
        System.out.println("이건 제대로 되나: " + dto);
        QuoteDto quoteDto = quoteService.findById((int) companyId);

        // quoteDto가 null이 아닌지 확인
        if (quoteDto != null) {
            quoteDto.setLocation(location);
            quoteService.update((int) companyId, quoteDto);
            return ResponseEntity.ok("배송 처리가 확인 되었습니다.");
        } else {
            // quoteDto가 null이면 적절한 응답 반환
            return ResponseEntity.badRequest().body("견적서를 찾을 수 없습니다.");
        }
    }


}
