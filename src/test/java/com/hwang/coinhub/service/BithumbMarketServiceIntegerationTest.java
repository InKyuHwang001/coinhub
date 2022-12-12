package com.hwang.coinhub.service;

import com.hwang.coinhub.dto.CoinBuyDTO;
import com.hwang.coinhub.dto.CoinSellDTO;
import com.hwang.coinhub.feign.BithumbFeignClient;
import com.hwang.coinhub.feign.response.BithumbResponse;
import com.hwang.coinhub.model.BithumbAssetEachStatus;
import com.hwang.coinhub.model.BithumbCoinPrice;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//@Disabled
@EnableFeignClients
@SpringBootTest
class BithumbMarketServiceIntegerationTest {
    @Autowired
    private BithumbMarketService bithumbMarketService;

    @Test
    void calculateFeeTest() throws Exception {
        Map<String, Double> result = bithumbMarketService.calculateFee();
        assertFalse(result.isEmpty());
    }

}