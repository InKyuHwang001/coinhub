package com.hwang.coinhub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommonMarketService {
//    @Autowired
//    MarketService bithumbMarketService;
//    @Autowired
//    MarketService upbitMarketService;
//    @Autowired
//    List<MarketService> marketServices;


   private final Map<String, MarketService> marketServices; //각각 마켓서비스의 이름이 String(키자리) 자리에 들어감 MarketService 에 빈들이 들어감

    public double getPrice(String market, String coin){
        MarketService marketService = getMarketService(marketServices, market);

        return marketService.getCoinCurrentPrice(coin);
    }

    public List<String> getCommonCoin(String fromMarket, String toMarket){
        //마켓 서비스 가져와
        MarketService formMarketService = getMarketService(marketServices, fromMarket);
        MarketService toMarketService = getMarketService(marketServices, toMarket);

        //각 마켓의 거래가능 코인 불러오기
        List<String> fromMarketCoins = formMarketService.getCoins();
        List<String> toMarketCoins = toMarketService.getCoins();

        //공통의 것 찾기
        List<String> result = new ArrayList<>();
        for (String eachCoin: fromMarketCoins){
            if (toMarketCoins.contains(eachCoin)){
                result.add(eachCoin);
            }
        }
        return result;
    }


    public static MarketService getMarketService(Map<String, MarketService> marketServices, String market){
        for (String key : marketServices.keySet()){
            if (key.substring(0, market.length()).equals(market.toLowerCase())){
                return marketServices.get(key);
            }
        }
        return null;
    }
}
