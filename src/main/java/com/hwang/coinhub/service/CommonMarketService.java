package com.hwang.coinhub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommonMarketService {
//    @Autowired
//    MarketService bithumbMarketService;
//    @Autowired
//    MarketService upbitMarketService;
//    @Autowired
//    List<MarketService> marketServices;


    @Autowired
    Map<String, MarketService> marketServices; //각각 마켓서비스의 이름이 String(키자리) 자리에 들어감 MarketService 에 빈들이 들어감

    public double getPrice(String market, String coin){
//        if(market.equals("bithumb")){ //==은 스트링 비교에 사용해선 안됨
//           return bithumbMarketService.getCoinCurrentPrice(coin);
//        } else if (market.equals("upbit")) {
//           return upbitMarketService.getCoinCurrentPrice(coin);
//        }
//        return 0;
        MarketService marketService = null;

        for (String key : marketServices.keySet()){
            if (key.substring(0, market.length()).equals(market.toLowerCase())){
                marketService = marketServices.get(key);
                break;
            }

        }
        return marketService.getCoinCurrentPrice(coin);
    }
}
