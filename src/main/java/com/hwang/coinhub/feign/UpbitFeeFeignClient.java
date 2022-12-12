package com.hwang.coinhub.feign;

import com.hwang.coinhub.model.UpbitCoinPrice;
import com.hwang.coinhub.model.UpbitMarketCode;
import com.hwang.coinhub.model.UpbitOrderBooks;
import com.hwang.coinhub.model.UpbitWithdrawalFee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "upbitFee", url = "https://api-manager.upbit.com/api/v1/kv")
public interface UpbitFeeFeignClient {
    @GetMapping("/UPBIT_PC_COIN_DEPOSIT_AND_WITHDRAW_GUIDE")
    UpbitWithdrawalFee getWithdrawalFee();

}
