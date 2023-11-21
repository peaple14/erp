package com.example.erp.report.dto;



import com.example.erp.member.entity.MemberEntity;
import com.example.erp.product.entity.ProductEntity;
import com.example.erp.report.entity.QuoteEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@ToString //soutp시험용도
public class QuoteDto {

    public long id; //아이디pk
    private String quotename; //견적서이름
    private ProductEntity product; //제품명,단위단가, //거래처,대표자,연락처
    private long quantity; //수량
    private long totalprice; //총단가
    private long receivemoney; //받은돈
    private LocalDate createdat; //견적서일자
    private LocalDate endat; //배송완료일자
    private MemberEntity writer; // 작성자
    private int location;//현재위치(나중에 배송중사용)
    private MemberEntity checkmember; //누가 최종확인했는지
    private String gopostcode; //출발우편번호
    private String godetailAddress; //출발상세주소
    private String goroadAddress; //출발도로명주소
    private String endpostcode; //받을우편번호
    private String enddetailAddress; //받을상세주소
    private String endroadAddress; //받을도로명주소
    private MultipartFile attachFile; //첨부파일
    private String uploadFileName; //첨부파일 원본이름
    private String storeFileName; //첨부파일 저장된이름



    //메서드이름 다음부터 잘좀짓기
    public static QuoteDto quoteDto(QuoteEntity quoteEntity) {
        QuoteDto quoteDto = new QuoteDto();
        quoteDto.setId(quoteEntity.getId());
        quoteDto.setQuotename(quoteEntity.getQuotename());
        quoteDto.setProduct(quoteEntity.getProduct());
        quoteDto.setQuantity(quoteEntity.getQuantity());
        quoteDto.setTotalprice(quoteEntity.getTotalPrice());
        quoteDto.setReceivemoney(quoteEntity.getReceive_money());
        quoteDto.setCreatedat(quoteEntity.getCreatedAt());
        quoteDto.setLocation(quoteEntity.getLocation());
        quoteDto.setCheckmember(quoteEntity.getCheckmember());
        quoteDto.setEndat(quoteEntity.getEndAt());
        quoteDto.setWriter(quoteEntity.getMember());
        quoteDto.setGopostcode(quoteEntity.getGopostcode());
        quoteDto.setGoroadAddress(quoteEntity.getGoroadAddress());
        quoteDto.setGodetailAddress(quoteEntity.getGodetailAddress());
        quoteDto.setEndpostcode(quoteEntity.getEndpostcode());
        quoteDto.setEndroadAddress(quoteEntity.getEndroadAddress());
        quoteDto.setEnddetailAddress(quoteEntity.getEnddetailAddress());
        quoteDto.setUploadFileName(quoteEntity.getUploadFileName());
        quoteDto.setStoreFileName(quoteEntity.getStoreFileName());
        return quoteDto;
    }

}
