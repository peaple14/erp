package com.example.erp.report.controller;

import com.example.erp.report.dto.ExpendDto;
import com.example.erp.report.service.ExpendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ExpendController {

    private final ExpendService expendService;

    // 지출 목록 표시
    @GetMapping("/expend_list")
    public String listExpends(Model model) {
        List<ExpendDto> expends = expendService.getAllExpends();
        model.addAttribute("expends", expends);
        return "report/expend/expend_list";
    }

    // 지출 추가 페이지 표시
    @GetMapping("/expend_add")
    public String expendAdd(Model model) {
        model.addAttribute("expendDto", new ExpendDto());
        return "report/expend/expend_add";
    }

    // 지출 추가 처리
    @PostMapping("/expend_add")
    public String expendAdd(@ModelAttribute ExpendDto expendDto) {
        expendService.save(expendDto);
        return "redirect:/expend_list";
    }

    // 지출 정보 표시
    @GetMapping("/expend_info/{id}")
    public String expendInfo(Model model, @PathVariable int id) {
        ExpendDto expendDto = expendService.findById(id);
        model.addAttribute("expend", expendDto);
        return "report/expend/expend_info";
    }

    // 지출 편집 페이지 표시
    @GetMapping("/expend_edit/{id}")
    public String expendEdit(Model model, @PathVariable int id) {
        ExpendDto expendDto = expendService.findById(id);
        model.addAttribute("expendDto", expendDto);
        return "report/expend/expend_edit";
    }

    // 지출 편집 처리
    @PostMapping("/expend_edit_ok")
    public String expendEditOk(@RequestParam(name = "id") int id, @ModelAttribute ExpendDto expendDto) {
        expendService.update(id, expendDto);
        return "redirect:/expend_list";
    }
}
