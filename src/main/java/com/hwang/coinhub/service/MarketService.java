package com.hwang.coinhub.service;

import com.hwang.coinhub.dto.CoinBuyDTO;
import com.hwang.coinhub.dto.CoinSellDTO;

import java.util.List;
import java.util.Map;

public interface MarketService {
    double getCoinCurrentPrice(String coin);

    List<String> getCoins();

    CoinBuyDTO calculateBuy(List<String> commonCoins, double amount);
    CoinSellDTO calculateSell(CoinBuyDTO buyDTO);
    Map<String/*Coin Name*/, Double/* Withdrawal Fee*/> calculateFee() throws Exception;

}
