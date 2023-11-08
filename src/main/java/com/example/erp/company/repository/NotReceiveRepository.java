package com.example.erp.company.repository;

import com.example.erp.company.entity.CompanyEntity;
import com.example.erp.company.entity.NotReceiveEntity;
import com.example.erp.product.entity.ProductEntity;
import com.example.erp.report.entity.QuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotReceiveRepository extends JpaRepository<NotReceiveEntity,Long> {

    List<NotReceiveEntity> findByQuote(QuoteEntity quote);


}
