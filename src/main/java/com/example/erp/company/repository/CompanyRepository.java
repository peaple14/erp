package com.example.erp.company.repository;

import com.example.erp.company.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity,Long> {

    List<CompanyEntity> findByStatus(String status);


}
