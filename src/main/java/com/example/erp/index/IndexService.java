package com.example.erp.index;

import com.example.erp.report.dto.QuoteDto;
import com.example.erp.report.entity.QuoteEntity;
import com.example.erp.report.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IndexService {

    private final QuoteService quoteService;

    public List<IndexDto> getAllIndexDto() {
        List<QuoteDto> quotes = quoteService.getAllQuotes();
        List<IndexDto> indexDtos = quotes.stream()
                .filter(quote -> quote.getEndat() != null) // 현재 팔린 견적서만
                .map(this::dtoToIndexDto)
                .collect(Collectors.toList());

        return indexDtos;
    }

    private IndexDto dtoToIndexDto(QuoteDto quote) {
        IndexDto indexDto = new IndexDto();
        indexDto.setSaleday(YearMonth.from(quote.getEndat()));
        indexDto.setCompanyname(quote.getProduct().getCompany().getCompanyName());
        indexDto.setProductname(quote.getProduct().getProductName());
        indexDto.setCount(quote.getTotalprice()/quote.getProduct().getPrice());
        indexDto.setPrice(quote.getTotalprice());

        return indexDto;
    }
}
