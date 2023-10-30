package com.example.erp.member.controller;

import com.example.erp.member.dto.LoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;


@Controller
public class MemberController {

    //로그인폼
    @GetMapping("login")
    public String login(){
        return "/login";    //첫로그인화면으로
    }

    //로그인시도
    @PostMapping("login")
    public String logins(@ModelAttribute LoginDto loginDto, HttpSession session, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {//에러날시
            return "redirect:/login"; //첫화면으로
        }
        //로그인 기능 구현
        return "index"; //로그인 끝나고 첫화면
    }

    //로그아웃
    @PostMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:index";
    }
}
