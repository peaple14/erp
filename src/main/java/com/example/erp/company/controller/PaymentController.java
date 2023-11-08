package com.example.erp.company.controller;

import com.example.erp.company.dto.NotReceiveDto;
import com.example.erp.company.service.NotReceiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final NotReceiveService notReceiveService;

    //입금 확인되었을때
    @PostMapping("/ccc")
    public ResponseEntity<String> receivePayment(@RequestBody NotReceiveDto dto ,HttpSession session) {
        long moneyAsLong = dto.getReceiveMoney(); // int 값을 long으로 변환
        long company_id = dto.getCompany_id(); // int 값을 long으로 변환
        if (session == null) {
            return ResponseEntity.badRequest().body("입금 처리 중 오류가 발생했습니다.");
        }
        notReceiveService.money_ok(company_id,moneyAsLong);
        System.out.println("money: " + moneyAsLong);
        System.out.println("회사아이디: " + company_id);
        System.out.println("오긴옴");
        // request에서 필요한 데이터를 처리합니다.
        return ResponseEntity.ok("입금 처리가 확인 되었습니다.");
    }
}
