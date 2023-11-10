    package com.example.erp.report.repository;

    import com.example.erp.report.entity.QuoteEntity;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    @Repository

    public interface QuoteRepository extends JpaRepository<QuoteEntity, Integer> {
    }
