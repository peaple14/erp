package com.example.erp.report.service;

import com.example.erp.company.entity.CompanyEntity;
import com.example.erp.company.repository.CompanyRepository;
import com.example.erp.product.entity.ProductEntity;
import com.example.erp.product.repository.ProductRepository;
import com.example.erp.report.dto.ExpendDto;
import com.example.erp.report.entity.ExpendEntity;
import com.example.erp.report.repository.ExpendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpendService {

    private final ExpendRepository expendRepository;
    private final CompanyRepository companyRepository;
    private final ProductRepository productRepository;

    // 지출 목록 조회
    @Transactional
    public List<ExpendDto> getAllExpends() {
        List<ExpendEntity> expends = expendRepository.findAll();
        return expends.stream()
                .map(ExpendDto::expendDto)
                .collect(Collectors.toList());
    }

    // 모든 회사 조회
    public List<CompanyEntity> getAllCompanies() {
        List<CompanyEntity> receivedCompanies = companyRepository.findByStatus("send"); //발주회사만 표시
        return receivedCompanies;    }

    // 모든 상품 조회
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    // 지출 추가
    @Transactional
    public void save(ExpendDto expendDto) {
        ExpendEntity expendEntity = ExpendEntity.toSaveEntity(expendDto);
        expendRepository.save(expendEntity);
    }

    // 지출 정보 조회
    @Transactional
    public ExpendDto findById(int id) {
        Optional<ExpendEntity> expendOptional = expendRepository.findById(id);
        return expendOptional.map(ExpendDto::expendDto).orElse(null);
    }

    // 지출 수정
    @Transactional
    public void update(int id, ExpendDto expendDto) {
        Optional<ExpendEntity> expendOptional = expendRepository.findById(id);
        expendOptional.ifPresent(expendEntity -> {
            expendEntity.update(expendDto);
            expendRepository.save(expendEntity);
        });
    }


}
