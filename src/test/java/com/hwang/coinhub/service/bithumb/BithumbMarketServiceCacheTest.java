package com.hwang.coinhub.service.bithumb;

import com.hwang.coinhub.constant.CacheConstants;
import com.hwang.coinhub.feign.BithumbFeignClient;
import com.hwang.coinhub.service.BithumbMarketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

//@Disabled
@EnableCaching
@EnableFeignClients
@SpringBootTest
class BithumbMarketServiceCacheTest {
    @Autowired
    private BithumbFeignClient bithumbFeignClient;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void calculateFeeTest() throws Exception {

        String parameter = "BTC";
        assertNull(cacheManager.getCache(CacheConstants.BITHUMB_COIN_PRICE).get(parameter));

        bithumbFeignClient.getCoinPrice(parameter);

        assertNotNull(cacheManager.getCache(CacheConstants.BITHUMB_COIN_PRICE).get(parameter));



    }

}