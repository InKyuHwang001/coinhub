package com.hwang.coinhub.service.upbit;

import com.hwang.coinhub.service.UpbitMarketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;

//@Disabled
@EnableFeignClients
@SpringBootTest
class UpbitMarketServiceIntegerationTest {
    @Autowired
    private UpbitMarketService upbitMarketService;

    @Test
    void calculateFeeTest() throws Exception {
        Map<String, Double> result = upbitMarketService.calculateFee();
        assertFalse(result.isEmpty());
    }

}