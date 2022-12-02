package com.hwang.coinhub.controller;

import com.hwang.coinhub.view.TranseferCalculateResponseView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor//꼭필요한 필드에 대해서만 생성자 생성
public class TranseferCalculateController {

    private final TranseferCalculateService transeferCalculateService;

    @GetMapping("/transefer-calculate")
    public TranseferCalculateResponseView getPrice(
            @RequestParam String fromMarket,
            @RequestParam String toMarket,
            @RequestParam double amount
    ){
        return transeferCalculateService.calculate(fromMarket, toMarket, amount);
    }
}
