package com.example.erp.company.service;

import com.example.erp.company.dto.NotReceiveDto;
import com.example.erp.company.entity.CompanyEntity;
import com.example.erp.company.repository.NotReceiveRepository;
import com.example.erp.report.dto.QuoteDto;
import com.example.erp.report.entity.QuoteEntity;
import com.example.erp.report.repository.QuoteRepository;
import com.example.erp.report.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotReceiveService {

    private final QuoteRepository quoteRepository;
    private final QuoteService quoteService;
    private final CompanyService companyService;


    //견적서,회사테이블에서 넣은돈만큼 처리.
    @Transactional
    public void setlist(Long id, Long money) {
        List<QuoteEntity> quotes = quoteRepository.findAll(); // 모든 quotes 가져오기
        List<QuoteEntity> filteredQuotes = new ArrayList<>(); // 일치하는 항목을 저장할 리스트
        for (QuoteEntity quote : quotes) {
            if (quote.getProduct().getCompany().getId() == id && quote.getCheckmember()!=null && quote.getReceive_money()!= quote.getTotalPrice() ) {//아이디가 같고,체킹이되었으며,돈이 지불되지않은것.
                filteredQuotes.add(quote); //일치하는것만 추가
            }
        }
        if (filteredQuotes == null) {

        }
        filteredQuotes.sort(Comparator.comparing(QuoteEntity::getCreatedAt));//오래된것부터 정렬

        for (QuoteEntity filteredQuote : filteredQuotes) {
            //10000원 - 3000원 - 50000원.
            if (filteredQuote.getTotalPrice() - filteredQuote.getReceive_money() - money < 0) { //낸돈이 더 많음.
                money = money - (filteredQuote.getTotalPrice() - filteredQuote.getReceive_money());
                filteredQuote.setReceive_money(money- filteredQuote.getReceive_money());//받은돈-받아야하는돈
                filteredQuote.setReceive_money(filteredQuote.getTotalPrice());//받을돈 = 받은돈으로 만듦.
                quoteService.update(filteredQuote.getId(),QuoteDto.quoteDto(filteredQuote));//업데이트
                companyService.companymoney(filteredQuote.getProduct().getCompany().getId(),money);
                System.out.println("낸돈 더많음:" + QuoteDto.quoteDto(filteredQuote));
                continue; //아직 낼돈있으니 계속.
            }

            if (filteredQuote.getTotalPrice() - filteredQuote.getReceive_money() - money == 0) { //정확히 맞춰서 돈냄.
                filteredQuote.setReceive_money(filteredQuote.getTotalPrice());//같게해준다음
                quoteService.update(filteredQuote.getId(),QuoteDto.quoteDto(filteredQuote));//업데이트
                companyService.companymoney(filteredQuote.getProduct().getCompany().getId(),money);
                money = money-money; //0원으로.
                System.out.println("낸돈 딱맞음:" + QuoteDto.quoteDto(filteredQuote));
                break; //낼돈없으니 끝냄
            }

            if (filteredQuote.getTotalPrice() - filteredQuote.getReceive_money() - money > 0) { //낸돈이 부족함.
                filteredQuote.setReceive_money(filteredQuote.getReceive_money() + money);//업데이트 해준다음
                quoteService.update(filteredQuote.getId(),QuoteDto.quoteDto(filteredQuote));//업데이트
                companyService.companymoney(filteredQuote.getProduct().getCompany().getId(),money);
                money = money-money; //0원으로.
                System.out.println("낸돈 부족함:" + QuoteDto.quoteDto(filteredQuote));
                break; //낼돈없으니 끝냄
            }
        }

        System.out.println("여기맞나:" + filteredQuotes);

    }

    @Scheduled(cron = "0 0 0 1 */3 ?") //3달에 1번되도록. Cron 표현식씀.
    public void test () {
        System.out.println("test");
    }




//    //삭제용
//    @Transactional
//    public void goodsDelete(Long id){
//        NotReceiveRepository.deleteById(id);
//    }


}
