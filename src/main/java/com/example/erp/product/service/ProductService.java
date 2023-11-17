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
        productEntity.setProductCode(productEntity.getCompany().getCompanyName() + productEntity.getId());
    }

    // 제품 상세 정보
    @Transactional
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
                .orElseThrow(() -> new EntityNotFoundException("제품: "+ id +"를 찾을 수 없습니다."));
        productEntity.update(productDto);
    }

    //배송완료시 갯수변경
    @Transactional
    public void countupdate(Long id, long count) { //상품id와 추가되거나 빠진갯수
        ProductDto productDto = findById(id);
        if (productDto == null) {
            throw new EntityNotFoundException("상품 ID에 해당하는 상품이 존재하지 않습니다.");
        }
        productDto.setCount(productDto.getCount() + count);
        update(id, productDto);
    }
}
