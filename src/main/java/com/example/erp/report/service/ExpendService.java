package com.example.erp.report.service;

import com.example.erp.company.entity.CompanyEntity;
import com.example.erp.company.repository.CompanyRepository;
import com.example.erp.company.service.CompanyService;
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
    private final CompanyService companyService;

    //지출결의서조회
    @Transactional
    public List<ExpendDto> getAllExpends() {
        List<ExpendEntity> expends = expendRepository.findAll();
        return expends.stream()
                .map(ExpendDto::expendDto)
                .collect(Collectors.toList());
    }

    //배송중인 지출결의서만 조회
    @Transactional
    public List<ExpendDto> getgoexpends(int location) {
        List<ExpendEntity> expends = expendRepository.findAll();
        List<ExpendDto> goExpends = expends.stream()
                .filter(expendEntity -> expendEntity.getLocation() == location)
                .map(ExpendDto::expendDto)
                .collect(Collectors.toList());

        return goExpends;
    }


    //모든 회사 조회후 추가창에 넣기
    @Transactional
    public List<ProductEntity> getFilteredProducts() {
        List<ProductEntity> products = getAllProducts();
        List<ProductEntity> filteredProducts = products.stream()
                .filter(product -> "receive".equals(product.getCompany().getStatus()))
                .collect(Collectors.toList());

        return filteredProducts;
    }

    //모든 상품 조회
    @Transactional
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }


    //지출결의서저장
    @Transactional
    public void save(ExpendDto expendDto) {
        ExpendEntity expendEntity = ExpendEntity.toSaveEntity(expendDto);
        expendEntity.setReceive_money(0);//현재 받은돈이 없다는뜻.
        expendRepository.save(expendEntity);
    }

    //지출결의서 자세히
    @Transactional
    public ExpendDto findById(int id) {
        Optional<ExpendEntity> expendOptional = expendRepository.findById(id);
        return expendOptional.map(ExpendDto::expendDto).orElse(null);
    }

    //지출결의서 수정
    @Transactional
    public void update(int id, ExpendDto expendDto) {
        Optional<ExpendEntity> expendOptional = expendRepository.findById(id);
        expendOptional.ifPresent(expendEntity -> {
            expendEntity.update(expendDto);
            expendRepository.save(expendEntity);
        });
    }
    //결제완료
    @Transactional
    public void check_ok(int id, ExpendDto expendDto) {
        Optional<ExpendEntity> expendOptional = expendRepository.findById(id);
        expendOptional.ifPresent(expendEntity -> {
            expendEntity.check_ok(expendDto);
            expendRepository.save(expendEntity);
        });
    }

    //add시 사용자 선택
    @Transactional
    public MemberEntity getMember(String id) {
        return memberRepository.findByUserId(id).orElse(null); //없을시 null반환.
    }





}