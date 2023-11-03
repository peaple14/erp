//package com.example.erp.unpaid.controller;
//
//import com.example.erp.unpaid.dto.UnpaidDto;
//import com.example.erp.unpaid.service.UnpaidService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequiredArgsConstructor
//public class UnpaidController {
//
//    private final UnpaidService unpaidService;
//
//    @GetMapping("/unpaid_list")
//    public String listUnpaids(Model model) {
//        List<UnpaidDto> unpaids = unpaidService.getAllUnpaids();
//        model.addAttribute("unpaids", unpaids);
//        return "unpaid/unpaid_list";
//    }
//
//    @GetMapping("/unpaid_add")
//    public String unpaidAdd() {
//        return "unpaid/unpaid_add";
//    }
//
//    @PostMapping("/unpaid_add")
//    public String unpaidAdd(UnpaidDto unpaidDto) {
//        unpaidService.save(unpaidDto);
//        return "redirect:/unpaid_list";
//    }
//
//    @GetMapping("/unpaid_info/{id}")
//    public String unpaidInfo(Model model, @PathVariable long id) {
//        UnpaidDto unpaidDto = unpaidService.findById(id);
//        model.addAttribute("unpaid", unpaidDto);
//        return "unpaid/unpaid_info";
//    }
//
//    @GetMapping("/unpaid_edit/{id}")
//    public String unpaidEdit(Model model, @PathVariable long id) {
//        UnpaidDto unpaidDto = unpaidService.findById(id);
//        model.addAttribute("unpaid", unpaidDto);
//        return "unpaid/unpaid_edit";
//    }
//
//    @PostMapping("/unpaid_edit_ok")
//    public String unpaidEditOk(@RequestParam(name = "id") Long id, @ModelAttribute UnpaidDto unpaidDto) {
//        unpaidService.update(id, unpaidDto);
//        return "redirect:/unpaid_list";
//    }
//}
