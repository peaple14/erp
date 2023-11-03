package com.example.erp.product.repository;

import com.example.erp.company.entity.CompanyEntity;
import com.example.erp.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    List<ProductEntity> findByCompany(CompanyEntity company);

}
