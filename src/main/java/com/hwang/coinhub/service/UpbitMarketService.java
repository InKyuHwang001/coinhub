package com.hwang.coinhub.service;

import com.hwang.coinhub.dto.CoinBuyDTO;
import com.hwang.coinhub.dto.CoinSellDTO;
import com.hwang.coinhub.feign.UpbitFeeFeignClient;
import com.hwang.coinhub.feign.UpbitFeignClient;
import com.hwang.coinhub.model.UpbitEachWithdrawalFee;
import com.hwang.coinhub.model.UpbitOrderBooks;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UpbitMarketService implements MarketService {
    private final UpbitFeignClient upbitFeignClient;
    private final UpbitFeeFeignClient upbitFeeFeignClient;

    public double getCoinCurrentPrice(String coin) {
        return upbitFeignClient.getCoinPrice("KRW-" + coin.toUpperCase())
                .get(0)
                .getTrade_price();
    }

    public List<String> getCoins() {
        List<String> result = new ArrayList<>();
        upbitFeignClient.getMarketCode().forEach(k -> {
            if (k.getMarket().startsWith("KRW")) {
                result.add(k.getMarket().substring(4).toUpperCase());
            }
        });
        return result;
    }

    public CoinBuyDTO calculateBuy(List<String> commonCoins, double amount) {
        Map<String, Double> amounts = new HashMap<>();
        Map<String, SortedMap<Double, Double>> orderBooks = new HashMap<>();
        commonCoins = commonCoins.stream().map(k -> "KRW-" + k.toUpperCase()).collect(Collectors.toList());

        List<UpbitOrderBooks> bithumbResponse = upbitFeignClient.getOrderBooks(commonCoins);
        bithumbResponse.forEach(k -> {
            double availableCurrency = amount;
            double availableCoin = 0;
            String coin = k.getMarket().substring(4);
            SortedMap<Double, Double> eachOrderBook = new TreeMap<>();

            List<UpbitOrderBooks.UpbitEachOrderBooks> eachOrderBooks = k.getOrderbook_units();
            eachOrderBooks.sort(Comparator.comparingDouble(UpbitOrderBooks.UpbitEachOrderBooks::getAsk_price)); // ask_price 기준 오름차순
            for (int i = 0; i < eachOrderBooks.size(); i++) {
                Double price = eachOrderBooks.get(i).getAsk_price();
                Double quantity = eachOrderBooks.get(i).getAsk_size();
                Double eachTotalPrice = price * quantity;
                double buyableCoinAmount = availableCurrency / price;

                // 만약 가격 넘으면 다음스텝,아니면 끝내기
                if (eachTotalPrice >= availableCurrency) { // 못넘어갈경우
                    availableCoin += buyableCoinAmount;
                    eachOrderBook.put(price, buyableCoinAmount);
                    availableCurrency = 0;
                    break;
                } else { // 다음 스텝 넘어갈경우
                    availableCoin += quantity;
                    eachOrderBook.put(price, quantity);
                    availableCurrency -= price * quantity;
                }
            }

            // 금액 모두 맞추지 못했을때 조건 추가 > 넣지 말기
            if (availableCurrency == 0) {
                amounts.put(coin, availableCoin);
                orderBooks.put(coin, eachOrderBook);
            }
        });

        return new CoinBuyDTO(amounts, orderBooks);
    }

    public CoinSellDTO calculateSell(Map<String, Double> sellingAmounts) {
        Map<String, Double> amounts = new HashMap<>();
        Map<String, SortedMap<Double, Double>> orderBooks = new HashMap<>();
        List<String> coins = sellingAmounts.keySet().stream().map(k -> "KRW-" + k.toUpperCase()).toList();

        List<UpbitOrderBooks> upbitResponse = upbitFeignClient.getOrderBooks(coins);

        upbitResponse.forEach(k -> {
            String coin = k.getMarket().substring(4);
            double sellCurrency = 0;
            Double availableCoin = sellingAmounts.get(coin);

            if (availableCoin != null) {
                SortedMap<Double, Double> eachOrderBook = new TreeMap<>(Comparator.reverseOrder());
                List<UpbitOrderBooks.UpbitEachOrderBooks> eachOrderBooks = k.getOrderbook_units();
                eachOrderBooks.sort(Comparator.comparingDouble(UpbitOrderBooks.UpbitEachOrderBooks::getBid_price).reversed()); // bid_price 기준 내림차순
                for (int i = 0; i < eachOrderBooks.size(); i++) {
                    Double price = eachOrderBooks.get(i).getBid_price();
                    Double quantity = eachOrderBooks.get(i).getBid_size();
                    Double eachTotalPrice = price * quantity;

                    // 만약 코인 양 더 많으면 끝내기
                    if (quantity >= availableCoin) { // 못넘어갈경우
                        sellCurrency += price * availableCoin;
                        eachOrderBook.put(price, availableCoin);
                        availableCoin = 0D;
                        break;
                    } else { // 다음 스텝 넘어갈경우
                        sellCurrency += eachTotalPrice;
                        eachOrderBook.put(price, quantity);
                        availableCoin -= quantity;
                    }
                }
                // 모두 팔지 못했을때 조건 추가 > 넣지 말기
                if (availableCoin == 0) {
                    amounts.put(coin, sellCurrency);
                    orderBooks.put(coin, eachOrderBook);
                }
            }
        });

        return new CoinSellDTO(amounts, orderBooks);
    }

    public Map<String/*Coin Name*/, Double/* Withdrawal Fee*/> calculateFee() throws Exception {
        return upbitFeeFeignClient.getWithdrawalFee().getData()
                .stream()
                .collect(Collectors.toMap(
                        UpbitEachWithdrawalFee::getCurrency,
                        UpbitEachWithdrawalFee::getWithdrawalFee
                ));
    }
}