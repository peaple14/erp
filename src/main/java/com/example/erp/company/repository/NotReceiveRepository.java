package com.example.erp.company.repository;

import com.example.erp.company.entity.NotReceiveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotReceiveRepository extends JpaRepository<NotReceiveEntity,Long> {
}
