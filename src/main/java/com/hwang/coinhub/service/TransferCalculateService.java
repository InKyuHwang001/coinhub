package com.hwang.coinhub.service;

import com.hwang.coinhub.dto.CoinBuyDTO;
import com.hwang.coinhub.dto.CoinSellDTO;
import com.hwang.coinhub.dto.TransferCalculateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransferCalculateService {

    private final CommonMarketService commonMarketService;
    private final Map<String, MarketService> marketServices;
    public TransferCalculateDTO calculate(String fromMarket, String toMarket, double amount){
        //form, to의 common coin
        List<String> commonCoins = commonMarketService.getCommonCoin(fromMarket, toMarket);

        MarketService fromMarketService = CommonMarketService.getMarketService(marketServices, fromMarket);
        MarketService toMarketService = CommonMarketService.getMarketService(marketServices, toMarket);

//        //from 얼마에서 살 수 있는지
//        CoinBuyDTO fromMarketBuyDTO =  fromMarketService.calculateBuy(commonCoins, amount);
//        //from 이체 수수료
//        Map<String, Double> fromMarketServiceFee = fromMarketService.calculateBuy(commonCoins, amount);
//        // to 얼마에 팔수 있는지
//        CoinSellDTO toMarketSellDTO = toMarketService.calculateSell(commonCoins, amount);
//        //가장 높은 값을 받을 수 있는 코인을 선택
//        String transferCoin = toMarketSellDTO.getAmount().keySet().get(0); //TODO: 가장 많은 현금 받는 코인 선택
//
//        return new TransferCalculateDTO(
//                transferCoin,
//                toMarketSellDTO.getAmount().get(transferCoin),
//                fromMarketBuyDTO.getOrderBooks().get(transferCoin),
//                toMarketSellDTO.getOrderBooks().get(transferCoin)
//        );

        return null;
    }

}
