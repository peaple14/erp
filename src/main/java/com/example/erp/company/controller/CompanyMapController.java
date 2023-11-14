package com.example.erp.company.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompanyMapController {


    @PostMapping ("/map/company")
    public String testEndpoint(
            @RequestParam(name = "latitude") Double latitude,
            @RequestParam(name = "longitude") Double longitude) {
        System.out.println("테스트latitude = " + latitude + ", longitude = " + longitude);

        // 받은 위도(latitude)와 경도(longitude)를 이용한 로직을 추가할 수 있습니다.

        return "Received latitude: " + latitude + ", longitude: " + longitude;
    }
}