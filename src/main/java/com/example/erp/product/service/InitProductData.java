package com.example.erp.product.service;

import com.example.erp.company.dto.CompanyDto;
import com.example.erp.company.entity.CompanyEntity;
import com.example.erp.company.repository.CompanyRepository;
import com.example.erp.product.dto.ProductDto;
import com.example.erp.product.entity.ProductEntity;
import com.example.erp.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.YearMonth;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitProductData {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final ProductService productService;

    @PostConstruct
    public void init() {
        if (productRepository.count() == 0) {
            insertProduct("김제품",  100, 5000,  findById(1), YearMonth.of(2022, 1));
            insertProduct("주제품",  150, 7000,  findById(2), YearMonth.of(2021, 12));
            insertProduct("상제품",  90,  10000, findById(3), YearMonth.of(2022, 3));
            insertProduct("박제품",  810,  20000, findById(4), YearMonth.of(2020, 9));
            insertProduct("김제품",  630,  18000, findById(1), YearMonth.of(2019, 8));
            insertProduct("주제품",  480,  32000, findById(2), YearMonth.of(2018, 5));
            insertProduct("상제품",  580,  6000, findById(3), YearMonth.of(2018, 5));
            insertProduct("박제품",  710,  8000, findById(4), YearMonth.of(2018, 5));

            System.out.println("제품 초기데이터 완료");
        } else {
            System.out.println("제품 초기데이터가 이미 존재합니다.");
        }
    }

    private void insertProduct(String productName, long count, long price, CompanyEntity makeCompany, YearMonth makeDay) {
        ProductDto productDto = new ProductDto();
        productDto.setProductname(productName);
        productDto.setCount(count);
        productDto.setPrice(price);
        productDto.setMakecompany(makeCompany);
        productDto.setMakeday(makeDay);

        productService.save(productDto);
    }

    private CompanyEntity createDummyCompany(String companyName) {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setCompanyName(companyName);
        return companyEntity;
    }

    @Transactional
    public CompanyEntity findById(long id) {
        Optional<CompanyEntity> optionalCompanyEntity = companyRepository.findById(id);
        CompanyEntity companyEntity = optionalCompanyEntity.get();
        return companyEntity;
    }


}
