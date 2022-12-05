package com.hwang.coinhub.service;

import com.hwang.coinhub.feign.BithumbFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BithumbMarketService implements MarketService {
    private final BithumbFeignClient bithumbFeignClient;
    public double getCoinCurrentPrice(String coin) {
        return Double.parseDouble(bithumbFeignClient.getCoinPrice(coin.toUpperCase()+"_KRW")
                .getData()
                .getClosing_price());

    }

    public List<String> getCoins(){
        //API를 이용하여 가져오기
        List<String> result = new ArrayList<>();
        bithumbFeignClient.getAssetStatus().getData().forEach((k,v) -> {
            if(v.getDeposit_status()==1&&v.getDeposit_status()==1){
                result.add(k.toUpperCase());
            }
        });
        return result;
    }

}
