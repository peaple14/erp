//package com.example.map_test.controller;
//
//
//import com.example.erp.company.dto.NotReceiveDto;
//import com.example.map_test.dto.StoreResDto;
//import com.example.map_test.dto.UserDto;
//import com.example.map_test.service.StoreService;
//import com.example.map_test.service.UserService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.util.List;
//
//@Slf4j
//@RequiredArgsConstructor
//@RestController
//public class test1 {
//
//    private final UserService userService;
//
//    // 로그인
//    @PostMapping("/login")
//    public String login(@RequestBody NotReceiveDto dto, HttpSession session) {
//        var result = userService.login(dto);
//        if (result == null) {
//            System.out.println("로그인 실패");
//            return null;
//        } else {
//            session.setAttribute("user", dto.getUserId());
//            return (String) session.getAttribute("user");
//        }
//    }
//
//    // 마이페이지
//    @PostMapping("/mypage")
//    public String userId(HttpSession session) {
//        return (String) session.getAttribute("user");
//    }
//
//    // 회원가입
//    @PostMapping("/signup")
//    public void register(UserDto dto, HttpServletResponse response) throws IOException {
//        System.out.println(dto);
//        userService.register(dto);
//        response.sendRedirect("/login");
//    }
//
//    // 검색 후 리스트 출력
//    @GetMapping("/logout")
//    public void logout(HttpServletResponse response, HttpSession session) throws IOException {
//        session.invalidate();
//        response.sendRedirect("/mypage");
//    }
//
//
//}