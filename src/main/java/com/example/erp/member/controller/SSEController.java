package com.example.erp.member.controller;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SSEController {

    @GetMapping("/sse-endpoint")
    public SseEmitter sseEndpoint() {
        SseEmitter emitter = new SseEmitter();
        try{

            System.out.println("실행은 되었다." + emitter);

            // 클라이언트에 대한 처리 또는 메시지 전송

        }catch(Exception e) {
            e.printStackTrace();
        }
        return emitter;
    }
}
