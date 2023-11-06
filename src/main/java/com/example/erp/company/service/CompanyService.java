package com.example.erp.company.service;

import com.example.erp.company.dto.CompanyDto;
import com.example.erp.company.entity.CompanyEntity;
import com.example.erp.company.repository.CompanyRepository;
import com.example.erp.faq.dto.NoticeDto;
import com.example.erp.faq.entity.NoticeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    // 모든 회사 조회
    public List<CompanyDto> getAllCompanies() {
        List<CompanyEntity> companyEntities = companyRepository.findAll();
        System.out.println("조회서비스: " + companyEntities);
        return companyEntities.stream()
                .map(CompanyDto::toCompanyDto)
                .collect(Collectors.toList());
    }

    //리스트띄어주기


    // 회사 추가
    @Transactional
    public void save(CompanyDto companyDto) {
        CompanyEntity companyEntity = CompanyEntity.toSaveEntity(companyDto);
        companyEntity.setMoney(0);
        companyRepository.save(companyEntity);
    }

    // 회사 상세 정보
    public CompanyDto findById(long id) {
        Optional<CompanyEntity> optionalCompanyEntity = companyRepository.findById(id);
        if (optionalCompanyEntity.isPresent()) {
            CompanyEntity companyEntity = optionalCompanyEntity.get();
            CompanyDto companyDto = CompanyDto.toCompanyDto(companyEntity);
            return companyDto;
        } else {
            return null;
        }
    }

    // 회사 정보 수정
    @Transactional
    public void update(Long id, CompanyDto companyDto) {
        CompanyEntity companyEntity = companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회사: " + id + " 를 찾을 수 없습니다."));
        companyEntity.update(companyDto);
    }
}
