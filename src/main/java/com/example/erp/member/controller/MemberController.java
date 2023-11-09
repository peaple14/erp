package com.example.erp.member.controller;

import com.example.erp.member.dto.LoginDto;
import com.example.erp.member.dto.MemberDto;
import com.example.erp.member.service.LoginService;
import com.example.erp.member.service.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final LoginService loginService;
    private final SseService sseService;

    //로그인폼
    @GetMapping("/")
    public String login(){
        return "/login";    //첫로그인화면으로
    }

    //로그인시도
    @PostMapping("login")
    public String logins(@ModelAttribute LoginDto loginDto, HttpSession session, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {//에러날시
            return "redirect:/login"; //첫화면으로
        }
        System.out.println("로그인테스트 첨 온것" + loginDto);
        MemberDto loginResult = loginService.login(loginDto);

        if (loginResult != null) {
            session.setAttribute("loginId", loginResult.getUserId());
            session.setAttribute("loginName", loginResult.getUserName());
            startSSESubscription(session); // 알림보내기용 sse추가
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

    // 알림 보내기용
    @PostMapping("/send-sse")
    public void sendSSE() {
        sseService.sendNotification("quote-added", "새로운 견적서가 추가되었습니다!");
    }

    // 메세지 보내기용
    private void startSSESubscription(HttpSession session) {
        SseEmitter emitter = new SseEmitter();
        sseService.addEmitter(emitter);
        session.setAttribute("sseEmitter", emitter);

        emitter.onCompletion(() -> {
            sseService.removeEmitter(emitter);
            session.removeAttribute("sseEmitter");
        });

        emitter.onTimeout(() -> {
            emitter.complete();
            sseService.removeEmitter(emitter);
            session.removeAttribute("sseEmitter");
        });
    }




}
