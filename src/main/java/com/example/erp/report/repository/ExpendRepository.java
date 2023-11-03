package com.example.erp.report.repository;

import com.example.erp.report.entity.ExpendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpendRepository extends JpaRepository<ExpendEntity, Integer> {
}
