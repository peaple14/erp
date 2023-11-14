package com.example.erp.apitest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class ApiController {

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @PostMapping("/getPostalCode")
    public ResponseEntity<String> getPostalCode(@RequestBody String address) {
        // 카카오맵 API 호출
        String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json?query=" + address;
        String kakaoApiKey = "61179dca181c32e776412ff335b7c5dc";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoApiKey);

        logger.info("Requesting Kakao API with address: {}", address);

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(apiUrl, String.class);

        logger.info("Kakao API response: {}", result);

        return ResponseEntity.ok(result);
    }
}