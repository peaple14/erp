package com.example.erp.index;

import com.example.erp.report.entity.QuoteEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class IndexController {

    private final IndexService indexService;



    @GetMapping("/index")
    public List<IndexDto> getIndexData() {
        System.out.println("여기:" + indexService.getAllIndexDto());
        return indexService.getAllIndexDto();
    }
}
