package com.hwang.coinhub.controller;

import com.hwang.coinhub.service.TransferCalculateService;
import com.hwang.coinhub.view.TransferCalculateResponseView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TransferCalculateController {
    private final TransferCalculateService transferCalculateService;

    @GetMapping("/transfer-calculate")
    public List<TransferCalculateResponseView> getPrice(
            @RequestParam String fromMarket,
            @RequestParam String toMarket,
            @RequestParam double amount
    ) throws Exception {
//        return new TransferCalculateResponseView("BTC", 123.45,
//                Map.of(123D, 456D),
//                Map.of(123D, 456D));
        return transferCalculateService.calculate(fromMarket, toMarket, amount)
                .stream()
                .map(k-> TransferCalculateResponseView.of(k, amount)).toList();
    }
}
