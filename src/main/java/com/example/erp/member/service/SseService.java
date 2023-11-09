package com.example.erp.member.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SseService {

    private final List<SseEmitter> emitters = new ArrayList<>();

    // 새로운 SseEmitter 추가
    public void addEmitter(SseEmitter emitter) {
        emitters.add(emitter);
    }

    // SseEmitter 제거
    public void removeEmitter(SseEmitter emitter) {
        emitters.remove(emitter);
    }

    // 모든 SseEmitter에 알림 보내기
    public void sendNotification(String eventName, String message) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name(eventName)
                        .data(message));
            } catch (IOException e) {
                // 예외 처리
            }
        }
    }
}
