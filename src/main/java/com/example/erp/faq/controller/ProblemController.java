package com.example.erp.faq.controller;

import com.example.erp.faq.dto.NoticeDto;
import com.example.erp.faq.dto.ProblemDto;
import com.example.erp.faq.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("templates/faq/problem")
public class ProblemController {

    private final ProblemService problemService;

    //고객불만접수 리스트 보여주기용(처음들어왔을때)
    @GetMapping("/problem_list")
    public String listNotices(Model model) {
        List<ProblemDto> problem = problemService.getAllProblems();
        model.addAttribute("problem", problem);
        return "faq/problem/problem_list";  //고객불만접수 리스트 띄어주기용
    }

    //글추가페이지
    @GetMapping("/problem_add")
    public String notice_add() {
        return "faq/problem/problem_add";  //글추가할 페이지입니다.
    }

    //글추가 처리 -> 권한이 admin일때만 되도록.
    @PostMapping("/problem_ok")
    public String notice_ok(@ModelAttribute ProblemDto problemDto, HttpSession session) throws IOException {
        System.out.println("세션내용:" +  session.getAttribute("loginId"));
        problemDto.setWriter(session.getAttribute("loginId").toString());
        problemService.save(problemDto); //writer은 session에서 받아욜 예정입니다.
        return "redirect:/problem_list"; //글추가 끝나고 갈페이지입니다.
    }

    //공지사항 세부정보
    @GetMapping("/problem_memo/{id}")
    public String n_memo(Model model, @PathVariable long id) {
        model.addAttribute("problem", problemService.findById(id));
        return "faq/problem/problem_memo";
    }

    //공지사항 수정폼
    @GetMapping("/problem_edit/{id}")
    public String re_notice(Model model, @PathVariable long id) {
        model.addAttribute("problem", problemService.findById(id));
        return "faq/problem/problem_edit";
    }

    //글수정 처리
    @PostMapping("/problem_edit_ok")
    public String edit_ok(@RequestParam(name = "id") Long id, @ModelAttribute ProblemDto problemDto){

        problemService.update(id,problemDto);
        return "redirect:problem_list";
    }
}
