package com.hwang.coinhub.feign;

import com.hwang.coinhub.constant.CacheConstants;
import com.hwang.coinhub.model.UpbitCoinPrice;
import com.hwang.coinhub.model.UpbitMarketCode;
import com.hwang.coinhub.model.UpbitOrderBooks;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "upbit", url = "https://api.upbit.com/v1")
public interface UpbitFeignClient {

    @Cacheable(CacheConstants.UPBIT_COIN_PRICE)
    @GetMapping("/ticker")
    List<UpbitCoinPrice> getCoinPrice(@RequestParam("markets") String coin);

    @Cacheable(CacheConstants.UPBIT_MARKET_CODE)
    @GetMapping("/market/all")
    List<UpbitMarketCode> getMarketCode();

    @Cacheable(CacheConstants.UPBIT_ORDER_BOOK)
    @GetMapping("/orderbook")
    List<UpbitOrderBooks> getOrderBooks(@RequestParam List<String> markets);
}
