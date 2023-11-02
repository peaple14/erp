package com.example.erp.member.repository;

import com.example.erp.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Long> {


    Optional<MemberEntity> findByUserId(String userId);

}
