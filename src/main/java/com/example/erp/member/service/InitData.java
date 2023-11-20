package com.example.erp.member.service;

import com.example.erp.member.dto.MemberDto;
import com.example.erp.member.entity.MemberEntity;
import com.example.erp.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitData {

    private final MemberRepository memberRepository;

    //계정데이터 추가용.
    @PostConstruct
    public void init() {
        if (memberRepository.count() == 0) { // 데이터가 없을 경우에만 초기화
            MemberDto memberDto = new MemberDto();
            memberDto.setUserId("111");
            memberDto.setUserPass("222");
            memberDto.setUserName("user1");
            memberDto.setUserauthority("USER");
            memberRepository.save(MemberEntity.toMemberEntity(memberDto));

            MemberDto memberDto2 = new MemberDto();
            memberDto2.setUserId("333");
            memberDto2.setUserPass("444");
            memberDto2.setUserName("admin");
            memberDto2.setUserauthority("ADMIN");
            memberRepository.save(MemberEntity.toMemberEntity(memberDto2));

            log.info("계정 초기 데이터를 추가");
        } else {
            log.info("계정 데이터가 이미 존재합니다. 초기화하지 않았습니다.");
        }
    }
}


