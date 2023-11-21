package com.example.erp.faq.entity;

import com.example.erp.faq.dto.NoticeDto;
import com.example.erp.member.entity.MemberEntity;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "notice_table")
public class NoticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String noticeTitle;

    @Column
    private String noticeMemo;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdTime;

    @ManyToOne
    @JoinColumn(name = "member_userId")
    private MemberEntity user;

    public static NoticeEntity toSaveEntity(NoticeDto noticeDTO, MemberEntity writer) {
        NoticeEntity noticeEntity = new NoticeEntity();
        noticeEntity.setNoticeTitle(noticeDTO.getNoticeTitle());
        noticeEntity.setNoticeMemo(noticeDTO.getNoticeMemo());
        noticeEntity.setUser(writer);

        return noticeEntity;
    }

    //더티체킹
    public void update(String noticeTitle, String noticeMemo) {
        if (noticeTitle != null) {
            this.noticeTitle = noticeTitle;
        }
        if (noticeMemo != null) {
            this.noticeMemo = noticeMemo;
        }
    }
}
