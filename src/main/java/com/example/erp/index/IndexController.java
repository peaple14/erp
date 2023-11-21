package com.example.erp.index;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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
