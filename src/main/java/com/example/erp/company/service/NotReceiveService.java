package com.example.erp.company.service;

import com.example.erp.company.dto.NotReceiveDto;
import com.example.erp.company.entity.NotReceiveEntity;
import com.example.erp.company.repository.NotReceiveRepository;
import com.example.erp.report.dto.QuoteDto;
import com.example.erp.report.entity.QuoteEntity;
import com.example.erp.report.repository.QuoteRepository;
import com.example.erp.report.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotReceiveService {

    private final QuoteRepository quoteRepository;
    private final QuoteService quoteService;
    private final CompanyService companyService;
    private final NotReceiveRepository notReceiveRepository;


    //견적서,회사테이블에서 넣은돈만큼 처리.
    @Transactional
    public void money_ok(Long id, Long money) {
        List<QuoteEntity> quotes = quoteRepository.findAll(); // 모든 quotes 가져오기
        List<QuoteEntity> filteredQuotes = new ArrayList<>(); // 일치하는 항목을 저장할 리스트
        for (QuoteEntity quote : quotes) {
            if (quote.getProduct().getCompany().getId() == id && quote.getCheckmember()!=null && quote.getReceive_money()!= quote.getTotalPrice() ) {//아이디가 같고,체킹이되었으며,돈이 지불되지않은것.
                filteredQuotes.add(quote); //일치하는것만 추가
            }
            System.out.println("현재 처리해야할것:" + filteredQuotes);
        }
        if (filteredQuotes == null) {
            System.out.println("이상한 id값 가져옴.");
        }
        filteredQuotes.sort(Comparator.comparing(QuoteEntity::getCreatedAt));//오래된것부터 정렬

        for (QuoteEntity filteredQuote : filteredQuotes) {
            //10000원 - 3000원 - 50000원.
            if (filteredQuote.getTotalPrice() - filteredQuote.getReceive_money() - money < 0) { //낸돈이 더 많음.
                money = money - (filteredQuote.getTotalPrice() - filteredQuote.getReceive_money());
                filteredQuote.setReceive_money(money- filteredQuote.getReceive_money());//받은돈-받아야하는돈
                filteredQuote.setReceive_money(filteredQuote.getTotalPrice());//받을돈 = 받은돈으로 만듦.
                quoteService.update(filteredQuote.getId(),QuoteDto.quoteDto(filteredQuote));//업데이트
                companyService.companymoney(filteredQuote.getProduct().getCompany().getId(),money);//초과로받을시 마이너스로.
                n_recieveDelete((long) filteredQuote.getId());//다냈으니 미수금 리스트에서
                System.out.println("낸돈 더많음:" + QuoteDto.quoteDto(filteredQuote));

                continue; //아직 낼돈있으니 계속.
            }

            if (filteredQuote.getTotalPrice() - filteredQuote.getReceive_money() - money == 0) { //정확히 맞춰서 돈냄.
                filteredQuote.setReceive_money(filteredQuote.getTotalPrice());//같게해준다음
                quoteService.update(filteredQuote.getId(),QuoteDto.quoteDto(filteredQuote));//업데이트
                companyService.companymoney(filteredQuote.getProduct().getCompany().getId(),money);
                money = money-money; //0원으로.
                n_recieveDelete((long) filteredQuote.getId());//다냈으니 미수금 리스트에서
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


//    @Scheduled(fixedRate = 5000) // 5초에 한번
    @Scheduled(cron = "0 0 0 * * ?")// 매일매일 하루에1번씩
    public void auto_not_receive() {
//        notReceiveRepository.deleteAll();//중복추가 방지 -> 안들어간것만 들어가도록 수정.

        //find by Quoteid 해서 찾은다음 넘어가기, 만약 없을시
        //현재:전부지우고,전부찾아서 전부중에 비교후 집어넣기
        //후:3개월지남&&안에없는것만 찾아서 넣기 -> findbyid가 필요한가?

        LocalDate currentDate = LocalDate.now();//현재날짜
        LocalDate golist = currentDate.minusMonths(3);// 3개월지나면 리스트로
        List<QuoteEntity> quoteEntities = quoteRepository.findAll();

        List<QuoteEntity> after3 = quoteEntities.stream()
                .filter(quote -> quote.getCreatedAt().isBefore(golist)//3개월이 지난것만 넣기
                        && quote.getReceive_money() != quote.getTotalPrice() //미수금 안낸것만.
                        && notReceiveRepository.findByQuote(quote).isEmpty())//안에없는것
                .collect(Collectors.toList());
        List<NotReceiveEntity> notReceiveEntities = after3.stream()//저장용
                .map(quote -> NotReceiveEntity.toSaveEntity(quote))
                .collect(Collectors.toList());

        notReceiveRepository.saveAll(notReceiveEntities);
        System.out.println("현재 날짜는: " + LocalDate.now());
    }


    //삭제용
    @Transactional
    public void n_recieveDelete(Long id){
        Optional<NotReceiveEntity> entityOptional = notReceiveRepository.findById(id);
        if (entityOptional.isPresent()) {
            notReceiveRepository.delete(entityOptional.get());
        }
    }

    //모든제품 리스트에 띄우기
    public List<NotReceiveDto> getallNReceive() {
        // NotReceiveRepository에서 모든 미수금 데이터를 가져와서 Dto로 변환하여 반환
        List<NotReceiveDto> notReceiveList = notReceiveRepository.findAll().stream()
                .map(NotReceiveDto::tonotReceiveDto)
                .collect(Collectors.toList());
        return notReceiveList;
    }


}
