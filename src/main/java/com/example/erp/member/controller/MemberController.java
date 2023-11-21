package com.example.erp.member.controller;

import com.example.erp.member.dto.LoginDto;
import com.example.erp.member.dto.MemberDto;
import com.example.erp.member.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpSession;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final LoginService loginService;


    //로그인폼
    @GetMapping("/")
    public String login() {
        return "/login";    //첫로그인화면으로
    }

    //로그인시도
    @PostMapping("main")
    public String logins(@ModelAttribute LoginDto loginDto, HttpSession session, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {//에러날시
            return "redirect:/main"; //첫화면으로
        }
        System.out.println("로그인테스트 첨 온것" + loginDto);
        MemberDto loginResult = loginService.login(loginDto);

        if (loginResult != null) {
            session.setAttribute("loginId", loginResult.getUserId());
            session.setAttribute("loginName", loginResult.getUserName());
            model.addAttribute("authority", loginResult.getUserauthority()); // 모델에 권한 추가
            System.out.println("로그인 성공");
            return "/index";
        } else {
            System.out.println("로그인 실패");
            return "redirect:/";
        }
    }

    //로그아웃
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        SseEmitter emitter = (SseEmitter) session.getAttribute("sseEmitter");
        if (emitter != null) {
            emitter.complete();
        }
        session.invalidate(); //세션타임아웃시간 기본 30분
        return "redirect:index";
    }


}
