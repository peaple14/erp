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
    public synchronized void addEmitter(SseEmitter emitter) {
        emitters.add(emitter);
    }

    // SseEmitter 제거
    public synchronized void removeEmitter(SseEmitter emitter) {
        emitters.remove(emitter);
        emitter.complete();
    }

    // 모든 SseEmitter에 알림 보내기
    public void sendNotification(String eventName, String message) {
        List<SseEmitter> deadEmitters = new ArrayList<>();

        deadEmitters.parallelStream().forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .name(eventName)
                        .data(message));
                System.out.println("이벤트 전송: " + eventName);
                System.out.println("메시지 전송: " + message);
                emitter.complete(); // 이 부분 추가
            } catch (IOException e) {
                System.out.println("에러발생" + e);
                deadEmitters.add(emitter);//에러발생시
            }
        });

        // 에러 발생한것 제거
        emitters.removeAll(deadEmitters);
    }
}
