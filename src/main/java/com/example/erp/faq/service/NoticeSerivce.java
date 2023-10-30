package com.example.erp.faq.service;


import com.example.erp.faq.dto.NoticeDto;
import com.example.erp.faq.entity.NoticeEntity;
import com.example.erp.faq.repository.NoticeRepository;
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


    //리스트띄어주기
    public List<NoticeDto> getAllNotices() {
        List<NoticeEntity> noticeEntities = noticeRepository.findAll();
        return noticeEntities.stream()
                .map(NoticeDto::toNoticeDTO)
                .collect(Collectors.toList());
    }

    //저장용
    @Transactional
    public void save(NoticeDto noticeDTO) throws IOException {
        NoticeEntity noticeEntity = NoticeEntity.toSaveEntity(noticeDTO);
        noticeRepository.save(noticeEntity); //entity값으로 반환
    }



    //게시글 자세히보기
    @Transactional
    public NoticeDto findById(long id) {
        Optional<NoticeEntity> optionalNoticeEntity = noticeRepository.findById(id);
        if (optionalNoticeEntity.isPresent()){
            NoticeEntity noticeEntity = optionalNoticeEntity.get();
            NoticeDto noticeDTO = NoticeDto.toNoticeDTO(noticeEntity);
            return noticeDTO;
        } else {
            return null;
        }
    }

    @Transactional
    public void update(Long id, NoticeDto noticeDto) {
        NoticeEntity noticeEntity = noticeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("공지사항: " + id + " 를 찾을수 없습니다."));
        noticeEntity.update(noticeDto.getNoticeTitle(),noticeDto.getNoticeMemo());
    }
}
