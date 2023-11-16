package com.example.erp.report.service;

import com.example.erp.company.entity.CompanyEntity;
import com.example.erp.company.repository.CompanyRepository;
import com.example.erp.member.entity.MemberEntity;
import com.example.erp.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    @Transactional
    public List<ExpendDto> getAllExpends() {
        List<ExpendEntity> expends = expendRepository.findAll();
        return expends.stream()
                .map(ExpendDto::expendDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<CompanyEntity> getAllCompanies() {
        List<CompanyEntity> receivedCompanies = companyRepository.findByStatus("receive");
        List<CompanyEntity> sendCompanies = companyRepository.findByStatus("send");
        return receivedCompanies;
    }

    @Transactional
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public void save(ExpendDto expendDto) {
        ExpendEntity expendEntity = ExpendEntity.toSaveEntity(expendDto);
        expendRepository.save(expendEntity);
    }

    @Transactional
    public ExpendDto findById(int id) {
        Optional<ExpendEntity> expendOptional = expendRepository.findById(id);
        return expendOptional.map(ExpendDto::expendDto).orElse(null);
    }

    @Transactional
    public void update(int id, ExpendDto expendDto) {
        Optional<ExpendEntity> expendOptional = expendRepository.findById(id);
        expendOptional.ifPresent(expendEntity -> {
            expendEntity.update(expendDto);
            expendRepository.save(expendEntity);
        });
    }

    @Transactional
    public void check_ok(int id, ExpendDto expendDto) {
        Optional<ExpendEntity> expendOptional = expendRepository.findById(id);
        expendOptional.ifPresent(expendEntity -> {
            expendEntity.check_ok(expendDto);
            expendRepository.save(expendEntity);
        });
    }

    @Transactional
    public MemberEntity getMember(String id) {
        return memberRepository.findByUserId(id).orElse(null);
    }
}