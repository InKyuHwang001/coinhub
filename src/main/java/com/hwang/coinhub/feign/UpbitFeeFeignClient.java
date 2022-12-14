package com.hwang.coinhub.feign;

import com.hwang.coinhub.constant.CacheConstants;
import com.hwang.coinhub.model.UpbitWithdrawalFee;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "upbitFee", url = "https://api-manager.upbit.com/api/v1/kv")
public interface UpbitFeeFeignClient {

    @Cacheable(CacheConstants.UPBIT_WITHDRAWAL_FEE)
    @GetMapping("/UPBIT_PC_COIN_DEPOSIT_AND_WITHDRAW_GUIDE")
    UpbitWithdrawalFee getWithdrawalFee();

}
