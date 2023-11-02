package com.example.erp.product.service;

import com.example.erp.company.entity.CompanyEntity;
import com.example.erp.company.repository.CompanyRepository;
import com.example.erp.product.dto.ProductDto;
import com.example.erp.product.entity.ProductEntity;
import com.example.erp.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;

    // 모든 제품 조회
    public List<ProductDto> getAllProducts() {
        List<ProductEntity> productEntities = productRepository.findAll();
        return productEntities.stream()
                .map(ProductDto::toProductDto)
                .collect(Collectors.toList());
    }

   //모든 회사 조회후 추가창에 넣기
   public List<CompanyEntity> getAllCompanies() {
       return companyRepository.findAll();
   }

    // 제품 추가
    @Transactional
    public void save(ProductDto productDto) {
        ProductEntity productEntity = ProductEntity.toSaveEntity(productDto);
        productRepository.save(productEntity);
    }

    // 제품 상세 정보
    public ProductDto findById(long id) {
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(id);
        if (optionalProductEntity.isPresent()) {
            ProductEntity productEntity = optionalProductEntity.get();
            ProductDto productDto = ProductDto.toProductDto(productEntity);
            return productDto;
        } else {
            return null;
        }
    }

    // 제품 정보 수정
    @Transactional
    public void update(Long id, ProductDto productDto) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("제품: " + id + " 를 찾을 수 없습니다."));
        productEntity.update(productDto);
    }
}
