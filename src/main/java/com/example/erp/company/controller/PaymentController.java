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
        long receiveMoney = dto.getReceiveMoney();
        long company_id = dto.getCompany_id();
        if (session == null) {
            return ResponseEntity.badRequest().body("입금 처리 중 오류가 발생했습니다.");
        }
        notReceiveService.money_ok(company_id,receiveMoney);
//        System.out.println("money: " + moneyAsLong);
//        System.out.println("회사아이디: " + company_id);
        return ResponseEntity.ok("입금 처리가 확인 되었습니다.");
    }
}
