package com.example.erp.faq.service;


import com.example.erp.faq.dto.NoticeDto;
import com.example.erp.faq.entity.NoticeEntity;
import com.example.erp.faq.repository.NoticeRepository;
import com.example.erp.member.entity.MemberEntity;
import com.example.erp.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeSerivce {

    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;


    //리스트띄어주기
    public List<NoticeDto> getAllNotices() {
        List<NoticeEntity> noticeEntities = noticeRepository.findAll();
        return noticeEntities.stream()
                .map(noticeEntity -> {
                    NoticeDto noticeDto = NoticeDto.toNoticeDto(noticeEntity);
                    noticeDto.setWriter(noticeEntity.getUser().getUserName());
                    return noticeDto;
                })
                .collect(Collectors.toList());
    }




    //저장용
    @Transactional
    public void save(NoticeDto noticeDTO) throws IOException {
        String writerId = noticeDTO.getWriter();
        //작성자 정보 찾기
        MemberEntity writer = memberRepository.findByUserId(writerId).orElse(null);
        NoticeEntity noticeEntity = NoticeEntity.toSaveEntity(noticeDTO, writer);
        noticeRepository.save(noticeEntity); // entity 값으로 반환
    }



    //게시글 자세히보기
    @Transactional
    public NoticeDto findById(long id) {
        Optional<NoticeEntity> optionalNoticeEntity = noticeRepository.findById(id);
        if (optionalNoticeEntity.isPresent()){
            NoticeEntity noticeEntity = optionalNoticeEntity.get();
            NoticeDto noticeDTO = NoticeDto.toNoticeDto(noticeEntity);
            noticeDTO.setWriter(noticeEntity.getUser().getUserName());
            return noticeDTO;
        } else {
            return null;
        }
    }

    //공지사항 수정용
    @Transactional
    public void update(Long id, NoticeDto noticeDto) {
        NoticeEntity noticeEntity = noticeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("공지사항: " + id + " 를 찾을수 없습니다."));
        noticeEntity.update(noticeDto.getNoticeTitle(),noticeDto.getNoticeMemo());
    }
}
