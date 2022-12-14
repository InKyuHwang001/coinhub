package com.hwang.coinhub.feign;

import com.hwang.coinhub.constant.CacheConstants;
import com.hwang.coinhub.feign.response.BithumbResponse;
import com.hwang.coinhub.model.BithumbAssetEachStatus;
import com.hwang.coinhub.model.BithumbCoinPrice;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "bithumb", url = "https://api.bithumb.com/public")
public interface BithumbFeignClient {

    @Cacheable(CacheConstants.BITHUMB_COIN_PRICE)
    @GetMapping("/ticker/{coin}")
    BithumbResponse<BithumbCoinPrice> getCoinPrice(@PathVariable("coin") String coin);

    @Cacheable(CacheConstants.BITHUMB_ASSET_STATUS)
    @GetMapping("/assetsstatus/ALL")
    BithumbResponse<Map<String, BithumbAssetEachStatus>> getAssetStatus();

    @Cacheable(CacheConstants.BITHUMB_ORDER_BOOK)
    @GetMapping("/orderbook/ALL_KRW")
    BithumbResponse<Map<String, Object>> getOrderBook();
}
