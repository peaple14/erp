package com.example.erp.company.service;

import com.example.erp.company.dto.CompanyDto;
import com.example.erp.company.entity.CompanyEntity;
import com.example.erp.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitCompanyData {

    private final CompanyRepository companyRepository;

    // 회사 데이터 추가용.
    @PostConstruct
    public void init() {
        if (companyRepository.count() == 0) { // 데이터가 없을 경우에만 초기화
            insertCompany("김회사", "김사장", "053-1111-1111", "receive", 0, "42829", "대구 달서구 화암로 342", "관리사무소");
            insertCompany("주회사", "주사장", "053-2222-2222", "send", 0, "42672", "대구 달서구 공원순환로 36", "관리사무소");
            insertCompany("상회사", "상사장", "062-3334-3333", "send", 0, "61187", "광주 북구 우치로 77", "2층");
            insertCompany("박회사", "박사장", "052-4444-4444", "send", 0, "44701", "울산 남구 돋질로 233", "3층");

            System.out.println("회사데이터 추가완료");
        } else {
            System.out.println("회사데이터가 이미존재함.");
        }
    }

    private void insertCompany(String companyName, String companyVip, String companyTel, String status,
                               long money, String postcode, String roadAddress, String detailAddress) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setCompanyName(companyName);
        companyDto.setCompanyVip(companyVip);
        companyDto.setCompanyTel(companyTel);
        companyDto.setStatus(status);
        companyDto.setMoney(money);
        companyDto.setPostcode(postcode);
        companyDto.setRoadAddress(roadAddress);
        companyDto.setDetailAddress(detailAddress);

        companyRepository.save(CompanyEntity.toSaveEntity(companyDto));
    }
}
