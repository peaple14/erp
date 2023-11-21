package com.example.erp.report.service;

import com.example.erp.company.dto.CompanyDto;
import com.example.erp.company.entity.CompanyEntity;
import com.example.erp.company.repository.CompanyRepository;
import com.example.erp.company.service.CompanyService;
import com.example.erp.member.entity.MemberEntity;
import com.example.erp.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final CompanyService companyService;

    //견적서조회
    @Transactional
    public List<QuoteDto> getAllQuotes() {
        List<QuoteEntity> quotes = quoteRepository.findAll();
        return quotes.stream()
                .map(QuoteDto::quoteDto)
                .collect(Collectors.toList());
    }

    //배송중인 견적서만 조회
    @Transactional
    public List<QuoteDto> getgoQuotes(int location) {
        List<QuoteEntity> quotes = quoteRepository.findAll();
        List<QuoteDto> goQuotes = quotes.stream()
                .filter(quoteEntity -> quoteEntity.getLocation() == location)
                .map(QuoteDto::quoteDto)
                .collect(Collectors.toList());

        return goQuotes;
    }


    //모든 회사 조회후 추가창에 넣기
    @Transactional
    public List<CompanyEntity> getAllCompanies() {
        List<CompanyEntity> receivedCompanies = companyRepository.findByStatus("receive"); //수주회사만 표시
        List<CompanyEntity> sendCompanies = companyRepository.findByStatus("send"); //발주회사만 표시

        return receivedCompanies;
    }

    //모든 상품 조회
    @Transactional
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }


    //견적서저장
    @Transactional
    public void save(QuoteDto quoteDto) {
        QuoteEntity quoteEntity = QuoteEntity.toSaveEntity(quoteDto);
        quoteEntity.setReceive_money(0);//현재 받은돈이 없다는뜻.
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
    //결제완료
    @Transactional
    public void check_ok(int id, QuoteDto quoteDto) {

        Optional<QuoteEntity> quoteOptional = quoteRepository.findById(id);
        quoteOptional.ifPresent(quoteEntity -> {
            quoteEntity.check_ok(quoteDto);
            quoteRepository.save(quoteEntity);
        });
    }

    //add시 사용자 선택
    @Transactional
    public MemberEntity getMember(String id) {
        return memberRepository.findByUserId(id).orElse(null); //없을시 null반환.
    }

    //미수금
    @Transactional
    public String mesugm(int id ,QuoteDto quoteDto) {

        //나중에 service로 옮기기.
        QuoteEntity quoteEntity = QuoteEntity.toSaveEntity(findById((int) quoteDto.id)); //모두 넣어서 견적서 완성본 만들기

        CompanyEntity companyEntity = quoteEntity.getProduct().getCompany();//회사 업데이트 준비
        companyEntity.setMoney((int) (quoteEntity.getTotalPrice() + companyEntity.getMoney())); //견적서에서 추가된 돈과 원래있던 미수금

        //회사 미수금 증가
        companyService.update(companyEntity.getId(), CompanyDto.toCompanyDto(companyEntity));
        check_ok(id,quoteDto);

        return "됨";

    }



}