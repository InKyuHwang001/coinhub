package com.hwang.coinhub.feign;

import com.hwang.coinhub.feign.response.BithumbRsponse;
import com.hwang.coinhub.model.BithumbAssetEachStatus;
import com.hwang.coinhub.model.BithumbCoinPrice;
import com.hwang.coinhub.model.UpbitCoinPrice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "bithumb", url = "https://api.bithumb.com/public/")
public interface BithumbFeignClient {

    @GetMapping("/ticker/{coin}")
    BithumbRsponse<BithumbCoinPrice> getCoinPrice(@PathVariable("coin") String coin);

    @GetMapping("/assetsstatus/ALL")
    BithumbRsponse<Map<String, BithumbAssetEachStatus>> getAssetStatus();
}
