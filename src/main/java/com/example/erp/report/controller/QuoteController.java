package com.example.erp.report.controller;

import com.example.erp.company.dto.CompanyDto;
import com.example.erp.company.entity.CompanyEntity;
import com.example.erp.company.service.CompanyService;
import com.example.erp.member.entity.MemberEntity;
import com.example.erp.product.entity.ProductEntity;
import com.example.erp.report.dto.QuoteDto;
import com.example.erp.report.entity.QuoteEntity;
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
    private final CompanyService companyService;

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
        System.out.println(products);
        model.addAttribute("quoteDto", new QuoteDto());
        model.addAttribute("products", products);
        return "report/quote/quote_add";
    }

    @PostMapping("/quote_add")
    public String quoteAdd(@ModelAttribute QuoteDto quoteDto, HttpSession session) {
        // 추가: HTML 폼에서 입력한 데이터를 로그로 출력
        System.out.println("견적서 추가 폼 제출 데이터: " + quoteDto);
        System.out.println("로그인 세션 정보: " + session.getAttribute("loginId"));
        quoteDto.setLocation(0);//
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
        List<ProductEntity> products = quoteService.getAllProducts();
        model.addAttribute("products", products);
        QuoteDto quoteDto = quoteService.findById(id);
        model.addAttribute("quoteDto", quoteDto);
        System.out.println("수정데이터값들:" + quoteDto);
        return "report/quote/quote_edit";
    }

    @PostMapping("/quote_edit_ok")
    public String quoteEditOk(@RequestParam(name = "id") int id, @ModelAttribute QuoteDto quoteDto) {
        System.out.println("수정후:" + quoteDto);
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
        ///////////////////////////////////

        //미수금 처리들 하기
        quoteService.mesugm(id, quoteDto);
        //나중에 service로 옮기기. ->오류 대비용 삭제안해둠.
//        QuoteEntity quoteEntity = QuoteEntity.toSaveEntity(quoteService.findById((int) quoteDto.id)); //모두 넣어서 견적서 완성본 만들기
//
//        CompanyEntity companyEntity = quoteEntity.getProduct().getCompany();//회사 업데이트 준비
//        companyEntity.setMoney((int) (quoteEntity.getTotalPrice() + companyEntity.getMoney())); //견적서에서 추가된 돈과 원래있던 미수금
//
//        //회사 미수금 증가
//        companyService.update(companyEntity.getId(), CompanyDto.toCompanyDto(companyEntity));
//        quoteService.check_ok(id,quoteDto);
        return "redirect:/quote_list";
    }
}
