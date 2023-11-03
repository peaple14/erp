package com.example.erp.report.service;

import com.example.erp.company.entity.CompanyEntity;
import com.example.erp.company.repository.CompanyRepository;
import com.example.erp.product.dto.ProductDto;
import com.example.erp.product.entity.ProductEntity;
import com.example.erp.product.repository.ProductRepository;
import com.example.erp.report.dto.QuoteDto;
import com.example.erp.report.entity.QuoteEntity;
import com.example.erp.report.repository.QuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final CompanyRepository companyRepository;
    private final ProductRepository productRepository;

    //견적서조회
    @Transactional
    public List<QuoteDto> getAllQuotes() {
        List<QuoteEntity> quotes = quoteRepository.findAll();
        return quotes.stream()
                .map(QuoteDto::quoteDto)
                .collect(Collectors.toList());
    }


    //모든 회사 조회후 추가창에 넣기
    public List<CompanyEntity> getAllCompanies() {
        List<CompanyEntity> receivedCompanies = companyRepository.findByStatus("receive"); //수주회사만 표시
        List<CompanyEntity> sendCompanies = companyRepository.findByStatus("send"); //발주회사만 표시
        System.out.println("recieve이거다:" + receivedCompanies);
        System.out.println("send이거다:" + sendCompanies);
        return receivedCompanies;
    }

    //모든 상품 조회
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }


    //견적서저장
    @Transactional
    public void save(QuoteDto quoteDto) {
        QuoteEntity quoteEntity = QuoteEntity.toSaveEntity(quoteDto);
        quoteRepository.save(quoteEntity);
    }

    //견적서 자세히
    @Transactional
    public QuoteDto findById(int id) {
        Optional<QuoteEntity> quoteOptional = quoteRepository.findById(id);
        return quoteOptional.map(QuoteDto::quoteDto).orElse(null);
    }

    //견적서 수정
    @Transactional
    public void update(int id, QuoteDto quoteDto) {
        Optional<QuoteEntity> quoteOptional = quoteRepository.findById(id);
        quoteOptional.ifPresent(quoteEntity -> {
            quoteEntity.update(quoteDto);
            quoteRepository.save(quoteEntity);
        });
    }

    //견적서 작성시
//    @Transactional
//    public List<QuoteDto> getAllQuotesWithDetails() {
//        List<QuoteEntity> quotes = quoteRepository.findAll();
//        return quotes.stream()
//                .map(quoteEntity -> {
//                    QuoteDto quoteDto = QuoteDto.quoteDto(quoteEntity);
//
//                    // 거래처 정보 가져오기
//                    if (quoteEntity.getCompany() != null) { //회사레퍼지토리의,아이디값을기준으로 모두
//                        CompanyEntity company = companyRepository.findById(quoteEntity.getCompany().getId()).orElse(null);
//                        if (company != null) {
//                            quoteDto.setCompany(company);
//                        }
//                    }
//
//                    // 제품 정보 가져오기
//                    if (quoteEntity.getProduct() != null) {
//                        // 선택한 거래처(company)에서 만든 제품 목록을 가져오도록 변경
//                        CompanyEntity company = companyRepository.findById(quoteEntity.getCompany().getId()).orElse(null);
//                        if (company != null) {
//                            // 거래처가 만든 모든 제품 목록을 가져옵니다.
//                            List<ProductEntity> products = productRepository.findByCompany(company);
//
//                            // 기존 코드에서는 제품 정보를 quoteEntity.getProduct()로 가져오는 대신
//                            // 원하는 방식으로 필요한 제품을 선택하도록 구현하실 수 있습니다.
//
//                            // 예를 들어, products 목록에서 특정 조건에 맞는 제품을 선택하도록 수정할 수 있습니다.
//                            // 여기서는 단순히 products 목록을 quoteDto에 설정하였으므로 필요한 처리를 추가해주세요.
//
//                            quoteDto.setProducts(products);
//                        }
//                    }
//
//                    return quoteDto;
//                })
//                .collect(Collectors.toList());
//    }
}
