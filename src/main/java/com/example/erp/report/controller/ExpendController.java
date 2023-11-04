package com.example.erp.report.controller;


import com.example.erp.product.entity.ProductEntity;
import com.example.erp.report.dto.ExpendDto;
import com.example.erp.report.service.ExpendService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ExpendController {

    private final ExpendService expendService;

    // 리스트 띄우기
    @GetMapping("/expend_list")
    public String listExpends(Model model) {
        List<ExpendDto> expends = expendService.getAllExpends();
        model.addAttribute("expends", expends);
        System.out.println("지출결의서 리스트띄우기:" + expends);
        return "report/expend/expend_list";
    }

    // 지출결의서 추가
    @GetMapping("/expend_add")
    public String expendAdd(Model model) {
        List<ProductEntity> products = expendService.getAllProducts();

        model.addAttribute("expendDto", new ExpendDto());
        model.addAttribute("products", products);
        return "report/expend/expend_add";
    }

    @PostMapping("/expend_add")
    public String expendAdd(@ModelAttribute ExpendDto expendDto, HttpSession session) {
        // 추가: HTML 폼에서 입력한 데이터를 로그로 출력
        System.out.println("지출결의서 추가 폼 제출 데이터: " + expendDto);
        System.out.println("로그인 세션 정보: " + session.getAttribute("loginId"));

        expendDto.setWriter(expendService.getMember((String) session.getAttribute("loginId")));
        expendService.save(expendDto);

        return "redirect:/expend_list";
    }

    @GetMapping("/expend_memo/{id}")
    public String expendInfo(Model model, @PathVariable int id) {
        ExpendDto expendDto = expendService.findById(id);
        model.addAttribute("expend", expendDto);
        System.out.println("자세히보기에 들어오는 데이터:" + expendDto);
        return "report/expend/expend_memo";
    }

    @GetMapping("/expend_edit/{id}")
    public String expendEdit(Model model, @PathVariable int id) {
        ExpendDto expendDto = expendService.findById(id);
        model.addAttribute("expendDto", expendDto);
        System.out.println("수정데이터값들:" + expendDto);
        return "report/expend/expend_edit";
    }

    @PostMapping("/expend_edit_ok")
    public String expendEditOk(@RequestParam(name = "id") int id, @ModelAttribute ExpendDto expendDto) {
        expendService.update(id, expendDto);
        return "redirect:/expend_list";
    }

    @GetMapping ("/expend_check_ok/{id}")
    public String check_ok(@PathVariable int id, HttpSession session, @ModelAttribute ExpendDto expendDto){
        System.out.println("결제완료됨." + id);
        System.out.println("로그인 세션 정보: " + session.getAttribute("loginId"));
        expendDto.setCheckmember(expendService.getMember((String) session.getAttribute("loginId")));
        expendDto.setIscheck(1);
        expendService.check_ok(id, expendDto);
        return "redirect:/expend_list";
    }
}
