package com.example.erp.member.controller;

import com.example.erp.member.service.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class SSEController {

    private final SseService sseService;

    @GetMapping("/sse-endpoint")
    public SseEmitter sseEndpoint() {
        SseEmitter emitter = new SseEmitter(10000L); //일단 10초로 설정
        try {
            // 클라이언트에 대한 처리 또는 메시지 전송
            sseService.addEmitter(emitter);
            System.out.println("연결 성공: " + emitter);

        } catch (Exception e) {
            System.out.println("오류메시지:" + e);
            e.printStackTrace();
        }
        return emitter;
    }
}
